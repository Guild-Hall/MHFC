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
import mhfc.net.common.util.parsing.syntax.operators.Operators;
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

	private static final Supplier<AST> TREE_BUILDER;
	private static final int VAL_EXPRESSION_ID;
	private static final int VAL_IDENTIFIER_ID;
	private static final int VAL_FUNCTIONCALL_ID;
	private static final int VAL_CLOSING_BRACKET_ID;

	private static final int OP_MEMBERACCESS_ID;
	private static final int OP_FUNCTIONCALL_ID;
	private static final int OP_ARGUMENTCONTINUE_ID;

	private static final int OP_OPENING_BRACKET_ID;
	private static final int OP_BRACKET_ID;

	private static final int OP_COMPLEMENT_ID;
	private static final int OP_NOT_ID;

	private static final int OP_MUL_ID;
	private static final int OP_DIV_ID;
	private static final int OP_MOD_ID;

	private static final int OP_ADD_ID;
	private static final int OP_SUB_ID;

	private static final int OP_LSHIFT_ID;
	private static final int OP_RSHIFT_ID;
	private static final int OP_URSHIFT_ID;

	private static final int OP_LESS_ID;
	private static final int OP_LESSEQ_ID;
	private static final int OP_GREATER_ID;
	private static final int OP_GREATEREQ_ID;

	private static final int OP_EQUAL_ID;
	private static final int OP_UNEQUAL_ID;

	private static final int OP_BITAND_ID;
	private static final int OP_BITOR_ID;
	private static final int OP_BITXOR_ID;

	private static final int OP_LOGICALAND_ID;
	private static final int OP_LOGICALOR_ID;

	static {
		SyntaxBuilder SYNTAX_BUILDER = new SyntaxBuilder();
		VAL_EXPRESSION_ID = SYNTAX_BUILDER.registerTerminal(IExpression.class);
		VAL_IDENTIFIER_ID = SYNTAX_BUILDER.registerTerminal(IdentifierLiteral.class, VAL_EXPRESSION_ID);
		VAL_FUNCTIONCALL_ID = SYNTAX_BUILDER.registerTerminal(FunctionCallLiteral.class, VAL_EXPRESSION_ID);

		VAL_CLOSING_BRACKET_ID = SYNTAX_BUILDER.registerTerminal(Void.class);

		OP_MEMBERACCESS_ID = SYNTAX_BUILDER.registerBinaryOperator(
				MemberOperator.class,
				VAL_EXPRESSION_ID,
				VAL_IDENTIFIER_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_FUNCTIONCALL_ID = SYNTAX_BUILDER.registerBinaryOperator(
				FunctionOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_FUNCTIONCALL_ID,
				true);
		OP_ARGUMENTCONTINUE_ID = SYNTAX_BUILDER.registerBinaryOperator(
				ArgumentContinuationOperator.class,
				VAL_FUNCTIONCALL_ID,
				VAL_EXPRESSION_ID,
				VAL_FUNCTIONCALL_ID,
				true);
		OP_BRACKET_ID = SYNTAX_BUILDER.registerUnaryOperator(
				BracketOp.class,
				true,
				VAL_CLOSING_BRACKET_ID,
				ElementType.VALUE,
				VAL_EXPRESSION_ID);
		OP_OPENING_BRACKET_ID = SYNTAX_BUILDER.registerUnaryOperator(
				OpeningBracketOp.class,
				true,
				VAL_EXPRESSION_ID,
				ElementType.PREFIX_OP,
				OP_BRACKET_ID);
		OP_COMPLEMENT_ID = SYNTAX_BUILDER
				.registerUnaryOperator(IOperator.class, true, VAL_EXPRESSION_ID, ElementType.VALUE, VAL_EXPRESSION_ID);
		OP_NOT_ID = SYNTAX_BUILDER
				.registerUnaryOperator(IOperator.class, true, VAL_EXPRESSION_ID, ElementType.VALUE, VAL_EXPRESSION_ID);
		OP_MUL_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_DIV_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_MOD_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_ADD_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_SUB_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_LSHIFT_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_RSHIFT_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_URSHIFT_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_LESS_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_LESSEQ_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_GREATER_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_GREATEREQ_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_EQUAL_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_UNEQUAL_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_BITAND_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_BITOR_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_BITXOR_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_LOGICALAND_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);
		OP_LOGICALOR_ID = SYNTAX_BUILDER.registerBinaryOperator(
				IBinaryOperator.class,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				VAL_EXPRESSION_ID,
				true);

		SYNTAX_BUILDER.declarePrecedence(OP_MEMBERACCESS_ID, OP_ARGUMENTCONTINUE_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_ARGUMENTCONTINUE_ID, OP_FUNCTIONCALL_ID);

		SYNTAX_BUILDER.declarePrecedence(OP_FUNCTIONCALL_ID, OP_COMPLEMENT_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_FUNCTIONCALL_ID, OP_NOT_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_FUNCTIONCALL_ID, OP_MUL_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_FUNCTIONCALL_ID, OP_DIV_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_FUNCTIONCALL_ID, OP_MOD_ID);

		SYNTAX_BUILDER.declarePrecedence(OP_COMPLEMENT_ID, OP_MUL_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_NOT_ID, OP_MUL_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_COMPLEMENT_ID, OP_DIV_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_NOT_ID, OP_DIV_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_COMPLEMENT_ID, OP_MOD_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_NOT_ID, OP_MOD_ID);

		SYNTAX_BUILDER.declareSamePrecedence(OP_MUL_ID, OP_DIV_ID, true);
		SYNTAX_BUILDER.declareSamePrecedence(OP_MUL_ID, OP_MOD_ID, true);
		SYNTAX_BUILDER.declareSamePrecedence(OP_DIV_ID, OP_MOD_ID, true);

		SYNTAX_BUILDER.declarePrecedence(OP_MUL_ID, OP_ADD_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_DIV_ID, OP_ADD_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_MOD_ID, OP_ADD_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_MUL_ID, OP_SUB_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_DIV_ID, OP_SUB_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_MOD_ID, OP_SUB_ID);

		SYNTAX_BUILDER.declareSamePrecedence(OP_ADD_ID, OP_SUB_ID, true);

		SYNTAX_BUILDER.declarePrecedence(OP_ADD_ID, OP_LSHIFT_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_SUB_ID, OP_LSHIFT_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_LSHIFT_ID, OP_RSHIFT_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_RSHIFT_ID, OP_URSHIFT_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_URSHIFT_ID, OP_LESS_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_LESS_ID, OP_LESSEQ_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_LESSEQ_ID, OP_GREATER_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_GREATER_ID, OP_GREATEREQ_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_GREATEREQ_ID, OP_EQUAL_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_EQUAL_ID, OP_UNEQUAL_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_UNEQUAL_ID, OP_BITAND_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_BITAND_ID, OP_BITXOR_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_BITXOR_ID, OP_BITOR_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_BITOR_ID, OP_LOGICALAND_ID);
		SYNTAX_BUILDER.declarePrecedence(OP_LOGICALAND_ID, OP_LOGICALOR_ID);

		SYNTAX_BUILDER.declarePrecedence(OP_LOGICALOR_ID, OP_OPENING_BRACKET_ID);

		SYNTAX_BUILDER.validate();
		AST mold = SYNTAX_BUILDER.newParseTree();

		TREE_BUILDER = mold::makeFreshTree;
	}

	private static abstract class StringSequence implements IBasicSequence {
		private final int[] codepoints;
		private int currentPoint;

		public StringSequence(int codepoint) {
			this.codepoints = new int[] { codepoint };
		}

		public StringSequence(String string) {
			if (Objects.requireNonNull(string).isEmpty()) {
				throw new IllegalArgumentException("string must not be empty");
			}
			this.codepoints = string.codePoints().toArray();
			assert this.codepoints.length > 0;
		}

		@Override
		public void reset() {
			currentPoint = 0;
		}

		@Override
		public SiftResult endOfStream() {
			return SiftResult.REJCECTED;
		}

		@Override
		public SiftResult accepting(int cp) {
			if (codepoints[currentPoint] != cp) {
				return SiftResult.REJCECTED;
			}
			currentPoint++;
			return currentPoint == codepoints.length ? SiftResult.FINISHED : SiftResult.ACCEPTED;
		}
	};

	private static class Identifier implements IBasicSequence {
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
			ast.pushValue(VAL_IDENTIFIER_ID, new IdentifierLiteral(sb.toString()));
		}
	}

	private static class Whitespace implements IBasicSequence {

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

	private static IBasicSequence makeUnaryOperator(int matchingChar, int ID, Supplier<IOperator<?, ?>> opSupplier) {
		return new StringSequence(matchingChar) {
			@Override
			public void pushOnto(AST ast) throws SyntaxErrorException {
				ast.pushUnaryOperator(ID, opSupplier.get());
			}
		};
	}

	private static IBasicSequence makeBinaryOperator(
			int matchingChar,
			int ID,
			Supplier<IBinaryOperator<?, ?, ?>> opSupplier) {
		return new StringSequence(matchingChar) {
			@Override
			public void pushOnto(AST ast) throws SyntaxErrorException {
				ast.pushBinaryOperator(ID, opSupplier.get());
			}
		};
	}

	private static IBasicSequence makeBinaryOperator(
			String matching,
			int ID,
			Supplier<IBinaryOperator<?, ?, ?>> opSupplier) {
		return new StringSequence(matching) {
			@Override
			public void pushOnto(AST ast) throws SyntaxErrorException {
				ast.pushBinaryOperator(ID, opSupplier.get());
			}
		};
	}

	private static class OpeningBracket extends StringSequence {
		public OpeningBracket() {
			super('(');
		}

		@Override
		public void pushOnto(AST ast) throws SyntaxErrorException {
			ast.pushUnaryOperator(OP_OPENING_BRACKET_ID, new OpeningBracketOp());
		}
	}

	private static class ClosingBracket extends StringSequence {
		public ClosingBracket() {
			super(')');
		}

		@Override
		public void pushOnto(AST ast) throws SyntaxErrorException {
			ast.pushValue(VAL_CLOSING_BRACKET_ID, null);
		}
	}

	private static class ContextSymbol extends StringSequence {
		public ContextSymbol() {
			super('$');
		}

		@Override
		public void pushOnto(AST ast) throws SyntaxErrorException {
			ast.pushValue(VAL_EXPRESSION_ID, new HolderLiteral(c -> c, "<context>"));
		}
	}

	private final List<IBasicSequence> sequences = new ArrayList<>();

	public ExpressionTranslator() {
		sequences.add(new Whitespace());
		sequences.add(new Comment());

		sequences.add(makeBinaryOperator('.', OP_MEMBERACCESS_ID, MemberOperator::new));
		sequences.add(makeBinaryOperator('|', OP_FUNCTIONCALL_ID, FunctionOperator::new));
		sequences.add(makeBinaryOperator(':', OP_ARGUMENTCONTINUE_ID, ArgumentContinuationOperator::new));

		sequences.add(makeBinaryOperator("==", OP_EQUAL_ID, Operators::makeEqualOp));
		sequences.add(makeBinaryOperator("!=", OP_UNEQUAL_ID, Operators::makeNotEqualOp));

		sequences.add(makeBinaryOperator("&&", OP_LOGICALAND_ID, Operators::makeConditionalAndOp));
		sequences.add(makeBinaryOperator("||", OP_LOGICALOR_ID, Operators::makeConditionalOrOp));

		sequences.add(makeBinaryOperator('*', OP_MUL_ID, Operators::makeMultiplyOp));
		sequences.add(makeBinaryOperator('/', OP_DIV_ID, Operators::makeDivideOp));
		sequences.add(makeBinaryOperator('%', OP_MOD_ID, Operators::makeModuloOp));

		sequences.add(makeBinaryOperator("<<", OP_LSHIFT_ID, Operators::makeLeftShiftOp));
		sequences.add(makeBinaryOperator(">>", OP_RSHIFT_ID, Operators::makeRightShiftOp));
		sequences.add(makeBinaryOperator(">>>", OP_URSHIFT_ID, Operators::makeUnsignedRightShiftOp));

		sequences.add(makeBinaryOperator("<=", OP_LESSEQ_ID, Operators::makeLessOrEqOp));
		sequences.add(makeBinaryOperator(">=", OP_GREATEREQ_ID, Operators::makeGreaterOrEqualOp));
		sequences.add(makeBinaryOperator("<", OP_LESS_ID, Operators::makeLessThanOp));
		sequences.add(makeBinaryOperator(">", OP_GREATER_ID, Operators::makeGreaterThanOp));

		sequences.add(makeUnaryOperator('!', OP_NOT_ID, Operators::makeLogicalComplementOp));
		sequences.add(makeUnaryOperator('~', OP_COMPLEMENT_ID, Operators::makeBitwiseComplementOp));

		sequences.add(makeBinaryOperator('+', OP_ADD_ID, Operators::makeAddOp));
		sequences.add(makeBinaryOperator('-', OP_SUB_ID, Operators::makeMinusOp));

		sequences.add(makeBinaryOperator('&', OP_BITAND_ID, Operators::makeAndOp));
		sequences.add(makeBinaryOperator('^', OP_BITXOR_ID, Operators::makeXorOp));
		sequences.add(makeBinaryOperator('|', OP_BITOR_ID, Operators::makeOrOp));

		sequences.add(new ContextSymbol());
		sequences.add(new OpeningBracket());
		sequences.add(new ClosingBracket());
		sequences.add(new Identifier());
		sequences.add(new StringConstant());
		sequences.add(new IntegerConstant());
	}

	public IValueHolder parse(String expression, IValueHolder contextValue) throws SyntaxErrorException {
		// Replace all comments
		// String noComment = commentPattern.matcher(expression).replaceAll(" ");
		return parseCleaned(expression, contextValue);
	}

	private IValueHolder parseCleaned(String cleanExpression, IValueHolder contextValue) throws SyntaxErrorException {
		// Cleaned as in: does only contain spaces as whitespace, doesn't
		// contain any comments and only one sequential whitespace.
		// So we can reset
		AST parseTree = TREE_BUILDER.get();

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
		return IExpression.class.cast(parseTree.getOverallValue()).asValue(contextValue);
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
