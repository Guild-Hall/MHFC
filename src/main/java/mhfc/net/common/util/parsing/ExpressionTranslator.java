package mhfc.net.common.util.parsing;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import mhfc.net.common.util.CompositeException;
import mhfc.net.common.util.parsing.syntax.IBasicSequence;
import mhfc.net.common.util.parsing.syntax.literals.FunctionCallLiteral;
import mhfc.net.common.util.parsing.syntax.literals.HolderLiteral;
import mhfc.net.common.util.parsing.syntax.literals.IExpression;
import mhfc.net.common.util.parsing.syntax.literals.IdentifierLiteral;
import mhfc.net.common.util.parsing.syntax.operators.ArgumentContinuationOperator;
import mhfc.net.common.util.parsing.syntax.operators.FunctionOperator;
import mhfc.net.common.util.parsing.syntax.operators.IBinaryOperator;
import mhfc.net.common.util.parsing.syntax.operators.IOperator;
import mhfc.net.common.util.parsing.syntax.operators.MemberOperator;
import mhfc.net.common.util.parsing.syntax.tree.AST;
import mhfc.net.common.util.parsing.syntax.tree.SyntaxBuilder;
import mhfc.net.common.util.parsing.syntax.tree.UnarySyntaxBuilder.ElementType;
import mhfc.net.common.util.parsing.valueholders.ValueHolders;
import net.minecraft.command.SyntaxErrorException;

public class ExpressionTranslator {
	private static class BracketOp implements IOperator<Void, IExpression> {
		private IExpression expression;

		public BracketOp(IExpression exp) {
			this.expression = Objects.requireNonNull(exp);
		}

		@Override
		public IExpression with(Void value) {
			return expression;
		}
	}

	private static class OpeningBracketOp implements IOperator<IExpression, BracketOp> {
		@Override
		public BracketOp with(IExpression value) {
			return new BracketOp(value);
		}
	}

	private static final SyntaxBuilder TREE_BUILDER = new SyntaxBuilder();
	private static final int VAL_EXPRESSION_ID;
	private static final int VAL_IDENTIFIER_ID;
	private static final int VAL_FUNCTIONCALL_ID;
	private static final int VAL_CLOSING_BRACKET_ID;

	private static final int OP_MEMBERACCESS_ID;
	private static final int OP_FUNCTIONCALL_ID;
	private static final int OP_ARGUMENTCONTINUE_ID;
	private static final int OP_OPENING_BRACKET_ID;
	private static final int OP_BRACKET_ID;

	static {
		VAL_EXPRESSION_ID = TREE_BUILDER.registerTerminal(IExpression.class);
		VAL_IDENTIFIER_ID = TREE_BUILDER.registerTerminal(IdentifierLiteral.class, VAL_EXPRESSION_ID);
		VAL_FUNCTIONCALL_ID = TREE_BUILDER.registerTerminal(FunctionCallLiteral.class, VAL_EXPRESSION_ID);

		VAL_CLOSING_BRACKET_ID = TREE_BUILDER.registerTerminal(Void.class);

		OP_MEMBERACCESS_ID = TREE_BUILDER.registerBinaryOperator(
				MemberOperator.class,
				VAL_EXPRESSION_ID,
				VAL_IDENTIFIER_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_FUNCTIONCALL_ID = TREE_BUILDER.registerBinaryOperator(
				FunctionOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_FUNCTIONCALL_ID,
				true);
		OP_ARGUMENTCONTINUE_ID = TREE_BUILDER.registerBinaryOperator(
				ArgumentContinuationOperator.class,
				VAL_FUNCTIONCALL_ID,
				VAL_EXPRESSION_ID,
				VAL_FUNCTIONCALL_ID,
				true);
		OP_BRACKET_ID = TREE_BUILDER.registerUnaryOperator(
				BracketOp.class,
				true,
				VAL_CLOSING_BRACKET_ID,
				ElementType.VALUE,
				VAL_EXPRESSION_ID);
		OP_OPENING_BRACKET_ID = TREE_BUILDER.registerUnaryOperator(
				OpeningBracketOp.class,
				true,
				VAL_EXPRESSION_ID,
				ElementType.PREFIX_OP,
				OP_BRACKET_ID);

		TREE_BUILDER.declarePrecedence(OP_MEMBERACCESS_ID, OP_FUNCTIONCALL_ID);
		TREE_BUILDER.declarePrecedence(OP_ARGUMENTCONTINUE_ID, OP_FUNCTIONCALL_ID);
		TREE_BUILDER.declarePrecedence(OP_MEMBERACCESS_ID, OP_ARGUMENTCONTINUE_ID);

		TREE_BUILDER.declarePrecedence(OP_FUNCTIONCALL_ID, OP_OPENING_BRACKET_ID);
		TREE_BUILDER.declarePrecedence(OP_ARGUMENTCONTINUE_ID, OP_OPENING_BRACKET_ID);
		TREE_BUILDER.declarePrecedence(OP_MEMBERACCESS_ID, OP_OPENING_BRACKET_ID);

		TREE_BUILDER.validate();
	}

	private static abstract class OneSymbolSequence implements IBasicSequence {
		private final int codepoint;

		public OneSymbolSequence(int codepoint) {
			this.codepoint = codepoint;
		}

		@Override
		public void reset() {}

		@Override
		public SiftResult endOfStream() {
			return SiftResult.REJCECTED;
		}

		@Override
		public SiftResult accepting(int cp) {
			if (codepoint == cp) {
				return SiftResult.FINISHED;
			}
			return SiftResult.REJCECTED;
		}
	};

	private class Identifier implements IBasicSequence {
		private StringBuilder sb = new StringBuilder();

		@Override
		public SiftResult accepting(int cp) {
			if (sb.length() == 0) {
				if (Character.isJavaIdentifierStart(cp)) {
					sb.appendCodePoint(cp);
					return SiftResult.ACCEPTED;
				}
				return SiftResult.REJCECTED;
			}
			if (Character.isJavaIdentifierPart(cp)) {
				sb.appendCodePoint(cp);
				return SiftResult.ACCEPTED;
			}
			return SiftResult.PAST_END;
		}

		@Override
		public void reset() {
			sb.setLength(0);
		}

		@Override
		public SiftResult endOfStream() {
			return this.sb.length() == 0 ? SiftResult.REJCECTED : SiftResult.PAST_END;
		}

		@Override
		public void pushOnto(AST ast) {
			ast.pushValue(VAL_IDENTIFIER_ID, new IdentifierLiteral(context, sb.toString()));
		}
	}

	private class Whitespace implements IBasicSequence {

		@Override
		public SiftResult accepting(int cp) {
			if (Character.isWhitespace(cp)) {
				return SiftResult.FINISHED;
			}
			return SiftResult.REJCECTED;
		}

		@Override
		public void reset() {}

		@Override
		public SiftResult endOfStream() {
			return SiftResult.PAST_END;
		}

		@Override
		public void pushOnto(AST ast) { /* ignore */ }
	}

	private static class Comment implements IBasicSequence {
		private static enum State {
			BEFORE,
			FIRST_STAR,
			DURING,
			SECOND_STAR;
		}

		private State state = State.BEFORE;

		@Override
		public SiftResult accepting(int cp) {
			switch (state) {
			case BEFORE:
				state = State.FIRST_STAR;
				return cp == '/' ? SiftResult.ACCEPTED : SiftResult.REJCECTED;
			case FIRST_STAR:
				state = State.DURING;
				return cp == '*' ? SiftResult.ACCEPTED : SiftResult.REJCECTED;
			case DURING:
				if (cp == '*') {
					state = State.SECOND_STAR;
				}
				return SiftResult.ACCEPTED;
			case SECOND_STAR:
				if (cp == '/') {
					return SiftResult.FINISHED;
				}
				if (cp != '*') {
					state = State.DURING;
				}
				return SiftResult.ACCEPTED;
			default:
				throw new IllegalStateException("unreachable");
			}
		}

		@Override
		public void reset() {
			state = State.BEFORE;
		}

		@Override
		public SiftResult endOfStream() {
			return SiftResult.REJCECTED;
		}

		@Override
		public void pushOnto(AST ast) { /* ignore */ }
	}

	private static class StringConstant implements IBasicSequence {
		private static enum State {
			BEGIN,
			ACTIVE;
		}

		private StringBuilder string = new StringBuilder();
		private State state = State.BEGIN;
		private boolean escaped = false;
		private List<Throwable> errors = new ArrayList<>();
		private int encountered = 0;

		@Override
		public SiftResult accepting(int cp) {
			if (state == State.BEGIN) {
				if (cp == '\"') {
					this.state = State.ACTIVE;
					this.encountered++;
					return SiftResult.ACCEPTED;
				}
				return SiftResult.REJCECTED;
			}
			if (!escaped) {
				if (cp == '\"') {
					return SiftResult.FINISHED;
				}
				if (cp == '\\') {
					escaped = true;
					return SiftResult.ACCEPTED;
				}
				string.appendCodePoint(cp);
				this.encountered++;
				return SiftResult.ACCEPTED;
			}
			switch (cp) {
			// \b \t \n \f \r \" \' \\
			case 'b':
				string.appendCodePoint('\b');
				break;
			case 't':
				string.appendCodePoint('\t');
				break;
			case 'n':
				string.appendCodePoint('\n');
				break;
			case 'f':
				string.appendCodePoint('\f');
				break;
			case 'r':
				string.appendCodePoint('\r');
				break;
			case '"':
				string.appendCodePoint('\"');
				break;
			case '\'':
				string.appendCodePoint('\'');
				break;
			case '\\':
				string.appendCodePoint('\\');
				break;
			default: {
				// Illegal escape sequence
				String error = "Illegal escape sequence \\" + String.valueOf(Character.toChars(cp)) + " at index "
						+ this.encountered;
				this.encountered++;
				this.errors.add(new IllegalArgumentException(error));
			}
			}
			this.escaped = false;
			return SiftResult.ACCEPTED;
		}

		@Override
		public void reset() {
			state = State.BEGIN;
			escaped = false;
			string.setLength(0);
			errors.clear();
			encountered = 0;
		}

		@Override
		public SiftResult endOfStream() {
			return SiftResult.REJCECTED;
		}

		@Override
		public void pushOnto(AST ast) {
			IValueHolder value;
			if (this.errors.size() == 0) {
				value = Holder.valueOrEmpty(this.string.toString());
			} else {
				value = ValueHolders.throwing(() -> new CompositeException(this.errors));
			}
			ast.pushValue(VAL_EXPRESSION_ID, new HolderLiteral(value));
		}
	}

	private static class IntegerConstant implements IBasicSequence {
		private static enum State {
			START,
			POSTSIGN,
			NUMBER;
		}

		private int base;
		private int length;
		private State state;
		private StringBuilder string = new StringBuilder();

		@Override
		public SiftResult accepting(int cp) {
			if (state == State.START) {
				if (cp == '+' || cp == '-') {
					string.appendCodePoint(cp);
					state = State.POSTSIGN;
					return SiftResult.ACCEPTED;
				}
			}
			if (state == State.START || state == State.POSTSIGN) {
				if (cp == '0') {
					base = 0;
					return SiftResult.ACCEPTED;
				}
				if (cp == 'o' && base == 0) {
					base = 8;
					state = State.NUMBER;
					return SiftResult.ACCEPTED;
				}
				if (cp == 'x' && base == 0) {
					base = 16;
					state = State.NUMBER;
					return SiftResult.ACCEPTED;
				}
				if (cp == 'b' && base == 0) {
					base = 2;
					state = State.NUMBER;
					return SiftResult.ACCEPTED;
				}
				state = State.NUMBER;
				if (base == 0) {
					// Literal 0, followed...
					string.appendCodePoint('0');
					base = 10;
					return SiftResult.PAST_END;
				}
			}
			if (state != State.NUMBER) {
				return SiftResult.REJCECTED;
			}
			if (Character.digit(cp, base) != -1) {
				length++;
				string.appendCodePoint(cp);
				return SiftResult.ACCEPTED;
			}
			if (length > 0) {
				return SiftResult.PAST_END;
			}
			return SiftResult.REJCECTED;
		}

		@Override
		public SiftResult endOfStream() {
			return length > 0 ? SiftResult.PAST_END : SiftResult.REJCECTED;
		}

		@Override
		public void reset() {
			base = 10;
			length = 0;
			state = State.START;
			string.setLength(0);
		}

		@Override
		public void pushOnto(AST ast) {
			IValueHolder value;
			try {
				int val = Integer.parseInt(string.toString(), base);
				value = Holder.valueOf(val);
			} catch (NumberFormatException nfe) {
				value = ValueHolders.throwing(() -> nfe);
			}
			ast.pushValue(VAL_EXPRESSION_ID, new HolderLiteral(value));
		}

	}

	private IBasicSequence makeBinaryOperator(int matchingChar, int ID, Supplier<IBinaryOperator<?, ?, ?>> opSupplier) {
		return new OneSymbolSequence(matchingChar) {
			@Override
			public void pushOnto(AST ast) throws SyntaxErrorException {
				ast.pushBinaryOperator(ID, opSupplier.get());
			}
		};
	}

	private static class OpeningBracket extends OneSymbolSequence {
		public OpeningBracket() {
			super('(');
		}

		@Override
		public void pushOnto(AST ast) throws SyntaxErrorException {
			ast.pushUnaryOperator(OP_OPENING_BRACKET_ID, new OpeningBracketOp());
		}
	}

	private static class ClosingBracket extends OneSymbolSequence {
		public ClosingBracket() {
			super(')');
		}

		@Override
		public void pushOnto(AST ast) throws SyntaxErrorException {
			ast.pushValue(VAL_CLOSING_BRACKET_ID, null);
		}
	}

	private class ContextSymbol extends OneSymbolSequence {
		public ContextSymbol() {
			super('$');
		}

		@Override
		public void pushOnto(AST ast) throws SyntaxErrorException {
			ast.pushValue(VAL_EXPRESSION_ID, new HolderLiteral(Holder.valueOf(context.getWrapper())));
		}
	}

	private final List<IBasicSequence> sequences = new ArrayList<>();
	private final Context context;

	public ExpressionTranslator(Context context) {
		this.context = context;
		sequences.add(new Whitespace());

		sequences.add(makeBinaryOperator('.', OP_MEMBERACCESS_ID, MemberOperator::new));
		sequences.add(makeBinaryOperator('|', OP_FUNCTIONCALL_ID, FunctionOperator::new));
		sequences.add(makeBinaryOperator(':', OP_ARGUMENTCONTINUE_ID, ArgumentContinuationOperator::new));

		sequences.add(new ContextSymbol());
		sequences.add(new Identifier());
		sequences.add(new StringConstant());
		sequences.add(new IntegerConstant());
		sequences.add(new Comment());
		sequences.add(new OpeningBracket());
		sequences.add(new ClosingBracket());
	}

	public IValueHolder parse(String expression) throws SyntaxErrorException {
		// Replace all comments
		// String noComment = commentPattern.matcher(expression).replaceAll(" ");
		return parseCleaned(expression);
	}

	private IValueHolder parseCleaned(String cleanExpression) throws SyntaxErrorException {
		// Cleaned as in: does only contain spaces as whitespace, doesn't
		// contain any comments and only one sequential whitespace.
		// So we can reset
		AST parseTree = TREE_BUILDER.newParseTree();

		IntBuffer expressionBuf = IntBuffer.wrap(cleanExpression.codePoints().toArray());
		expressionBuf.mark();

		Iterator<IBasicSequence> basicSequences = sequences.iterator();
		IBasicSequence currentSequence = nextSequenceOrSyntaxError(basicSequences, expressionBuf);

		parse_loop: while (true) {
			if (!expressionBuf.hasRemaining()) {
				switch (currentSequence.endOfStream()) {
				case ACCEPTED:
				case FINISHED:
					throw new SyntaxErrorException("Can't accept end-of-stream, only PAST_END or REJECTED");
				case PAST_END:
					currentSequence.pushOnto(parseTree);
					break parse_loop;
				case REJCECTED:
				default:
					currentSequence.reset();
					expressionBuf.reset();
					currentSequence = nextSequenceOrSyntaxError(basicSequences, expressionBuf);
					break;
				}
				continue;
			}
			int cp = expressionBuf.get();
			switch (currentSequence.accepting(cp)) {
			case ACCEPTED:
				break;
			case PAST_END:
				// Rewind by 1
				expressionBuf.position(expressionBuf.position() - 1);
				/* no break */
			case FINISHED:
				// Push the syntax stack
				expressionBuf.mark();
				currentSequence.pushOnto(parseTree);
				basicSequences = sequences.iterator();
				currentSequence = nextSequenceOrSyntaxError(basicSequences, expressionBuf);
				break;

			case REJCECTED:
			default:
				// Reset to the mark, try next sequence
				expressionBuf.reset();
				currentSequence = nextSequenceOrSyntaxError(basicSequences, expressionBuf);
				break;
			}
		}
		return ((IExpression) parseTree.getOverallValue()).asValue();
	}

	private IBasicSequence nextSequenceOrSyntaxError(Iterator<IBasicSequence> sequences, IntBuffer stream) {
		if (sequences.hasNext()) {
			IBasicSequence next = sequences.next();
			next.reset();
			return next;
		}
		// Collect the next few integers
		int collected = Math.min(stream.remaining(), 20);
		int position = stream.position();
		int[] out = new int[collected];
		stream.get(out);
		String asString = new String(out, 0, collected);
		String message = "Encountered unexpected sequence: (first 20) \"" + asString + "\" at " + position;
		throw new SyntaxErrorException(message);
	}
}
