package mhfc.net.common.util.parsing.syntax;

import java.util.List;
import java.util.Stack;

import com.google.common.collect.Lists;

import mhfc.net.common.util.parsing.syntax.Symbol.IExpressionSymbol;
import mhfc.net.common.util.parsing.syntax.Symbol.IFunctionCallSymbol;
import mhfc.net.common.util.parsing.syntax.Symbol.IdentifierSymbol;
import mhfc.net.common.util.parsing.syntax.Symbol.MemberAccessSymbol;
import mhfc.net.common.util.parsing.syntax.Symbol.SymbolType;
import mhfc.net.common.util.parsing.syntax.Symbol.TerminalSymbol;

public class SyntaxRules {
	public static final ISyntaxRule memberExpression = (s) -> {
		if (s.size() < 3)
			return false;
		Symbol memberNameS = s.pop();
		Symbol terminal = s.pop();
		Symbol instanceS = s.pop();
		if (memberNameS.type != SymbolType.IDENTIFIER || terminal.type != SymbolType.TERMINAL_SYMBOL
				|| !instanceS.type.isExpression()) {
			s.push(instanceS);
			s.push(terminal);
			s.push(memberNameS);
			return false;
		}
		if (((TerminalSymbol) terminal.symbol).codepoint != '.') {
			s.push(instanceS);
			s.push(terminal);
			s.push(memberNameS);
			return false;
		}
		IdentifierSymbol memberName = (IdentifierSymbol) memberNameS.symbol;
		IExpressionSymbol instance = ((IExpressionSymbol) instanceS.symbol);
		Symbol symbol = new Symbol();
		symbol.type = SymbolType.MEMBER_ACCESS;
		symbol.symbol = new Symbol.MemberAccessSymbol(instance, memberName);
		s.push(symbol);
		return true;
	};

	public static final ISyntaxRule functionCall = (s) -> {
		if (s.size() < 3)
			return false;
		Symbol topOfStack = s.pop();
		Symbol terminal = s.pop();
		Symbol argumentOne = s.pop();
		if (terminal.type != SymbolType.TERMINAL_SYMBOL || !argumentOne.type.isExpression()) {
			s.push(argumentOne);
			s.push(terminal);
			s.push(topOfStack);
			return false;
		}
		if (((TerminalSymbol) terminal.symbol).codepoint != '|') {
			s.push(argumentOne);
			s.push(terminal);
			s.push(topOfStack);
			return false;
		}
		if (topOfStack.type == SymbolType.IDENTIFIER) {
			IdentifierSymbol functionName = (IdentifierSymbol) topOfStack.symbol;
			IExpressionSymbol arg = ((IExpressionSymbol) argumentOne.symbol);
			Symbol symbol = new Symbol();
			symbol.type = SymbolType.FREE_FUNCTION_CALL;
			symbol.symbol = new Symbol.FreeFunctionCallSymbol(functionName, Lists.newArrayList(arg));
			s.push(symbol);
			return true;
		}
		if (topOfStack.type == SymbolType.MEMBER_ACCESS) {
			MemberAccessSymbol memberFunc = ((MemberAccessSymbol) topOfStack.symbol);
			IExpressionSymbol arg = ((IExpressionSymbol) argumentOne.symbol);
			Symbol symbol = new Symbol();
			symbol.type = SymbolType.MEMBER_FUNCTION_CALL;
			symbol.symbol = new Symbol.MemberFunctionCallSymbol(memberFunc.member, memberFunc.instance,
					Lists.newArrayList(arg));
			s.push(symbol);
			return true;
		}
		s.push(argumentOne);
		s.push(terminal);
		s.push(topOfStack);
		return false;
	};

	public static final ISyntaxRule functionCalleePromotion = (s) -> {
		if (s.size() < 3)
			return false;
		Symbol member = s.pop();
		Symbol terminal = s.pop();
		Symbol functionCall = s.pop();
		if (member.type != SymbolType.IDENTIFIER || terminal.type != SymbolType.TERMINAL_SYMBOL
				|| !functionCall.type.isFunctionCall()) {
			s.push(functionCall);
			s.push(terminal);
			s.push(member);
			return false;
		}
		TerminalSymbol terminalRaw = (TerminalSymbol) terminal.symbol;
		if (terminalRaw.codepoint != '.') {
			s.push(functionCall);
			s.push(terminal);
			s.push(member);
			return false;
		}
		IFunctionCallSymbol functionCallS = (IFunctionCallSymbol) functionCall.symbol;
		if (functionCallS.getArguments().size() != 1) {
			// Not in <expr> | <callee>:<expr>.<member>
			// So, we should promote the expression
			Stack<Symbol> localStack = new Stack<>();
			List<IExpressionSymbol> args = functionCallS.getArguments();
			IExpressionSymbol lastArgs = args.get(args.size() - 1);
			// TODO: push to local stack <expr>.<member>
			// reduce it locally
			// copy it on our stack if changed and return true, else false
			// ->> will reduce our stack again, rince and repeat if necessary

			s.push(functionCall);
			s.push(terminal);
			s.push(member);
			return false;
		}
		// Only promotable in the format <expr> | <callee>.<member>
		IdentifierSymbol memberS = (IdentifierSymbol) member.symbol;

		Symbol symbol = new Symbol();
		symbol.type = SymbolType.MEMBER_FUNCTION_CALL;
		symbol.symbol = new Symbol.MemberFunctionCallSymbol(memberS, functionCallS.getCallee(),
				functionCallS.getArguments());
		s.push(symbol);
		return true;
	};

	public static final ISyntaxRule functionCallArgument = (s) -> {
		if (s.size() < 3)
			return false;
		Symbol argument = s.pop();
		Symbol terminal = s.pop();
		Symbol functionCall = s.pop();
		if (!argument.type.isExpression() || terminal.type != SymbolType.TERMINAL_SYMBOL
				|| !functionCall.type.isFunctionCall()) {
			s.push(functionCall);
			s.push(terminal);
			s.push(argument);
			return false;
		}
		TerminalSymbol terminalRaw = (TerminalSymbol) terminal.symbol;
		if (terminalRaw.codepoint != ':') {
			s.push(functionCall);
			s.push(terminal);
			s.push(argument);
			return false;
		}
		IFunctionCallSymbol functionCallS = (IFunctionCallSymbol) functionCall.symbol;
		functionCallS.getArguments().add((IExpressionSymbol) argument.symbol);
		s.push(functionCall);
		return true;
	};

	public static final ISyntaxRule bracketExpression = (s) -> {
		if (s.size() < 3)
			return false;
		Symbol rightBracket = s.pop();
		Symbol expression = s.pop();
		Symbol leftBracket = s.pop();
		if (rightBracket.type != SymbolType.TERMINAL_SYMBOL || leftBracket.type != SymbolType.TERMINAL_SYMBOL
				|| !expression.type.isExpression()) {
			s.push(leftBracket);
			s.push(expression);
			s.push(rightBracket);
			return false;
		}
		TerminalSymbol right = (TerminalSymbol) rightBracket.symbol;
		TerminalSymbol left = (TerminalSymbol) leftBracket.symbol;
		if (right.codepoint != ')' || left.codepoint != '(') {
			s.push(leftBracket);
			s.push(expression);
			s.push(rightBracket);
			return false;
		}
		Symbol symbol = new Symbol();
		symbol.type = SymbolType.BRACKET_EXPRESSION;
		symbol.symbol = new Symbol.BracketExpressionSymbol(expression);
		s.push(symbol);
		return true;
	};
}
