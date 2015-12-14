package mhfc.net.common.util.parsing.syntax;

import java.util.List;
import java.util.Objects;

import mhfc.net.common.util.parsing.IExpression;
import mhfc.net.common.util.parsing.expressions.Access;
import mhfc.net.common.util.parsing.expressions.Constant;
import mhfc.net.common.util.parsing.expressions.FreeFunctionCall;
import mhfc.net.common.util.parsing.expressions.FunctionCall;
import mhfc.net.common.util.parsing.expressions.Variable;

public class Symbol {
	public static interface ISymbol {
	};

	public static enum SymbolType {
		TERMINAL_SYMBOL(TerminalSymbol.class), //
		IDENTIFIER(IdentifierSymbol.class), //
		MEMBER_ACCESS(MemberAccessSymbol.class), //
		FREE_FUNCTION_CALL(FreeFunctionCallSymbol.class), //
		MEMBER_FUNCTION_CALL(MemberFunctionCallSymbol.class), //
		BRACKET_EXPRESSION(BracketExpressionSymbol.class), //
		CONSTANT(ConstantSymbol.class), //
		;

		public final Class<? extends ISymbol> snytaxClass;

		private SymbolType(Class<? extends ISymbol> syntaxClass) {
			this.snytaxClass = Objects.requireNonNull(syntaxClass);
		}

		public boolean isExpression() {
			return IExpressionSymbol.class.isAssignableFrom(this.snytaxClass);
		}

		public boolean isFunctionCall() {
			return IFunctionCallSymbol.class.isAssignableFrom(this.snytaxClass);
		}
	}

	public static Symbol makeTerminal(int cp) {
		Symbol s = new Symbol();
		s.type = SymbolType.TERMINAL_SYMBOL;
		s.symbol = new TerminalSymbol(cp);
		return s;
	}

	public static class TerminalSymbol implements ISymbol {
		public final int codepoint;

		public TerminalSymbol(int cp) {
			this.codepoint = cp;
		}

		@Override
		public String toString() {
			return String.valueOf(Character.toChars(codepoint));
		}
	}

	public static interface IExpressionSymbol extends ISymbol {
		IExpression asExpression();
	}

	public static class IdentifierSymbol implements IExpressionSymbol {
		public final String identifier;

		public IdentifierSymbol(String identifier) {
			this.identifier = Objects.requireNonNull(identifier);
		}

		@Override
		public IExpression asExpression() {
			return new Variable(identifier.toString());
		}

		@Override
		public String toString() {
			return identifier;
		}
	}

	public static class MemberAccessSymbol implements IExpressionSymbol {
		public final IExpressionSymbol instance;
		public final IdentifierSymbol member;

		public MemberAccessSymbol(IExpressionSymbol instance, IdentifierSymbol member) {
			this.instance = Objects.requireNonNull(instance);
			this.member = Objects.requireNonNull(member);
		}

		@Override
		public IExpression asExpression() {
			return new Access(instance.asExpression(), member.toString());
		}

		@Override
		public String toString() {
			return instance.toString() + "." + member;
		}
	}

	public static class ConstantSymbol implements IExpressionSymbol {
		public final Constant value;

		public ConstantSymbol(Constant value) {
			this.value = Objects.requireNonNull(value);
		}

		@Override
		public IExpression asExpression() {
			return this.value;
		}

		@Override
		public String toString() {
			return value.toString();
		}
	}

	public static interface IFunctionCallSymbol extends IExpressionSymbol {
		List<IExpressionSymbol> getArguments();

		/**
		 * The called method, most likely either an IdentifierSymbol or a
		 * MemberAccessSymbol
		 */
		IExpressionSymbol getCallee();
	}

	public static class FreeFunctionCallSymbol implements IFunctionCallSymbol {
		public final List<IExpressionSymbol> arguments;
		public final IdentifierSymbol functionName;

		public FreeFunctionCallSymbol(IdentifierSymbol functionName, List<IExpressionSymbol> arguments) {
			this.functionName = Objects.requireNonNull(functionName);
			this.arguments = Objects.requireNonNull(arguments);
		}

		@Override
		public IExpression asExpression() {
			return new FreeFunctionCall(functionName.identifier,
					arguments.stream().map(IExpressionSymbol::asExpression).toArray(IExpression[]::new));
		}

		@Override
		public String toString() {
			return functionName + arguments.toString();
		}

		@Override
		public List<IExpressionSymbol> getArguments() {
			return this.arguments;
		}

		@Override
		public IExpressionSymbol getCallee() {
			return this.functionName;
		}
	}

	public static class MemberFunctionCallSymbol implements IFunctionCallSymbol {
		public final List<IExpressionSymbol> arguments;
		public final MemberAccessSymbol callee;

		public MemberFunctionCallSymbol(IdentifierSymbol functionName, IExpressionSymbol instance,
				List<IExpressionSymbol> arguments) {
			this.callee = new MemberAccessSymbol(instance, functionName);
			this.arguments = Objects.requireNonNull(arguments);
		}

		@Override
		public IExpression asExpression() {
			return new FunctionCall(callee.instance.asExpression(), callee.member.identifier,
					arguments.stream().map(IExpressionSymbol::asExpression).toArray(IExpression[]::new));
		}

		@Override
		public String toString() {
			return callee.toString() + arguments.toString();
		}

		@Override
		public List<IExpressionSymbol> getArguments() {
			return this.arguments;
		}

		@Override
		public IExpressionSymbol getCallee() {
			return callee;
		}
	}

	public static class BracketExpressionSymbol implements IExpressionSymbol {
		public final Symbol innerSymbol;

		public BracketExpressionSymbol(Symbol inner) {
			this.innerSymbol = Objects.requireNonNull(inner);
			if (!inner.type.isExpression())
				throw new IllegalArgumentException();
		}

		@Override
		public IExpression asExpression() {
			return ((IExpressionSymbol) innerSymbol.symbol).asExpression();
		}

		@Override
		public String toString() {
			return "(" + innerSymbol.symbol.toString() + ")";
		}
	}

	public ISymbol symbol;
	public SymbolType type;

	@Override
	public String toString() {
		return "{type: " + type + ", symbol: " + symbol + "}";
	}
}
