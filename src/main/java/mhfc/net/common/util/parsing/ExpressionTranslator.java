package mhfc.net.common.util.parsing;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

import mhfc.net.common.util.parsing.syntax.BasicSequences;
import mhfc.net.common.util.parsing.syntax.IBasicSequence;
import mhfc.net.common.util.parsing.syntax.ISyntaxRule;
import mhfc.net.common.util.parsing.syntax.Symbol;
import mhfc.net.common.util.parsing.syntax.Symbol.IExpressionSymbol;
import mhfc.net.common.util.parsing.syntax.SyntaxRules;
import net.minecraft.command.SyntaxErrorException;

public class ExpressionTranslator {
	private static final Pattern commentPattern = Pattern.compile("/\\*.*?\\*/");

	private static final List<ISyntaxRule> syntaxRules = new ArrayList<>();
	private static final List<IBasicSequence> sequences = new ArrayList<>();

	static {
		sequences.add(new BasicSequences.Identifier());
		sequences.add(new BasicSequences.Whitespace());
		sequences.add(new BasicSequences.StringConstant());
		sequences.add(new BasicSequences.IntegerConstant());

		syntaxRules.add(SyntaxRules.bracketExpression);
		syntaxRules.add(SyntaxRules.functionCall);
		syntaxRules.add(SyntaxRules.functionCallArgument);
		syntaxRules.add(SyntaxRules.functionCalleePromotion);
		syntaxRules.add(SyntaxRules.memberExpression);
	}

	public static IExpression parse(String expression) throws IllegalArgumentException {
		// Replace all comments
		String noComment = commentPattern.matcher(expression).replaceAll(" ");
		return parseCleaned(noComment);
	}

	private static IExpression parseCleaned(String cleanExpression) {
		// Cleaned as in: does only contain spaces as whitespace, doesn't
		// contain any comments and only one sequential whitespace.
		// Can't be avoided...
		Stack<Symbol> syntaxStack = new Stack<>();
		// So we can reset
		IntBuffer expressionBuf = IntBuffer.wrap(cleanExpression.codePoints().toArray());
		expressionBuf.mark();

		Iterator<IBasicSequence> basicSequences = sequences.iterator();
		IBasicSequence currentSequence = null;

		syntax_parse: while (true) {
			while (expressionBuf.hasRemaining()) {
				int cp = expressionBuf.get();
				if (currentSequence == null) {
					if (!basicSequences.hasNext()) {
						// All sequences tried, push raw
						syntaxStack.push(Symbol.makeTerminal(cp));
						reduce(syntaxStack);
						basicSequences = sequences.iterator();
						expressionBuf.mark();
						continue;
					}
					currentSequence = basicSequences.next();
				}
				switch (currentSequence.accepting(cp)) {
				case FINISHED:
					// Rewind by 1, and push the syntax stack
					expressionBuf.position(expressionBuf.position() - 1);
					expressionBuf.mark();
					currentSequence.pushOnto(syntaxStack);
					currentSequence.reset();
					reduce(syntaxStack);
					basicSequences = sequences.iterator();
					currentSequence = null;
					break;
				case REJCECTED:
					// Reset to the mark, try next sequence
					currentSequence.reset();
					expressionBuf.reset();
					currentSequence = null;
					break;
				case ACCEPTED:
				default:
					break;
				}
			}
			if (currentSequence != null) {
				switch (currentSequence.endOfStream()) {
				case ACCEPTED:
				case FINISHED:
					currentSequence.pushOnto(syntaxStack);
					currentSequence.reset();
					break;
				case REJCECTED:
					currentSequence.reset();
					expressionBuf.reset();
					currentSequence = null;
					continue syntax_parse;
				default:
					break;
				}
			}
			break;
		}
		reduce(syntaxStack);
		if (syntaxStack.size() == 1 && syntaxStack.peek().type.isExpression()) {
			return ((IExpressionSymbol) syntaxStack.peek().symbol).asExpression();
		}
		throw new SyntaxErrorException("Illegal syntax, parsed stack: " + syntaxStack);
	}

	public static boolean reduce(Stack<Symbol> syntaxStack) {
		// System.out.println("Reducing:" + syntaxStack);
		boolean changed = false;
		match_loop: while (true) {
			for (ISyntaxRule rule : syntaxRules) {
				if (rule.reduce(syntaxStack)) {
					changed = true;
					continue match_loop;
				}
			}
			break match_loop;
		}
		// System.out.println(syntaxStack);
		// System.out.println();
		return changed;
	}
}
