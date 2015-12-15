package mhfc.net.common.util.parsing.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.expressions.Constant;
import mhfc.net.common.util.parsing.syntax.Symbol.IdentifierSymbol;
import mhfc.net.common.util.parsing.syntax.Symbol.SymbolType;

public class BasicSequences {
	/**
	 * @see Double#valueOf(String)
	 */
	// private static final String Digits = "(\\p{Digit}+)";
	// private static final String HexDigits = "(\\p{XDigit}+)";
	// private static final String Exp = "[eE][+-]?" + Digits;
	// private static final String fpRegex = ("[+-]?(" + // Optional sign
	// character
	// // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
	// // Note: to differ between integers and floating points, we use
	// // Digits . Digits_opt ExponentPart_opt FloatTypeSuffix_opt
	// "(((" + Digits + "(\\.)(" + Digits + "?)(" + Exp + ")?)|" +
	// // . Digits ExponentPart_opt FloatTypeSuffix_opt
	// "(\\.(" + Digits + ")(" + Exp + ")?)|" +
	// // Hexadecimal strings
	// "((" +
	// // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
	// "(0[xX]" + HexDigits + "(\\.)?)|" +
	// // 0[xX] HexDigits_opt . HexDigits BinaryExponent
	// // FloatTypeSuffix_opt
	// "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" + ")[pP][+-]?" + Digits
	// + "))" + "[fFdD]?))");
	// private static final Pattern integerRegex =
	// Pattern.compile("^[-+]?(0|[1-9][0-9]*)");

	public static class Identifier implements IBasicSequence {
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
			return SiftResult.FINISHED;
		}

		@Override
		public void reset() {
			sb.setLength(0);
		}

		@Override
		public void pushOnto(Stack<Symbol> tokenStack) {
			Symbol s = new Symbol();
			s.type = SymbolType.IDENTIFIER;
			s.symbol = new IdentifierSymbol(sb.toString());
			tokenStack.push(s);
		}

		@Override
		public SiftResult endOfStream() {
			return this.sb.length() == 0 ? SiftResult.REJCECTED : SiftResult.FINISHED;
		}
	}

	public static class Whitespace implements IBasicSequence {
		private int length = 0;

		@Override
		public SiftResult accepting(int cp) {
			length++;
			if (Character.isWhitespace(cp)) {
				return SiftResult.ACCEPTED;
			}
			if (length > 1) {
				// Finished means rules are reset, prevent recursion
				return SiftResult.FINISHED;
			}
			// Not finished, no rule reset
			return SiftResult.REJCECTED;
		}

		@Override
		public void reset() {
			length = 0;
		}

		@Override
		public void pushOnto(Stack<Symbol> tokenStack) {
			// Noop
		}

		@Override
		public SiftResult endOfStream() {
			return SiftResult.FINISHED;
		}
	}

	public static class StringConstant implements IBasicSequence {
		private static class CompositeException extends IllegalArgumentException {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3901532003007263456L;
			private List<Throwable> exceptions = new ArrayList<Throwable>();

			public CompositeException(List<Throwable> exceptions) {
				super("");
				this.exceptions = new ArrayList<>(exceptions);
			}

			@Override
			public String toString() {
				return super.toString() + exceptions.toString();
			}
		}

		private static enum State {
			BEGIN, ACTIVE, ENDED;
		}

		private StringBuilder string = new StringBuilder();
		private State state = State.BEGIN;
		private boolean escaped = false;
		private List<Throwable> errors = new ArrayList<>();
		private int encountered = 0;

		@Override
		public SiftResult accepting(int cp) {
			if (state == State.ENDED)
				return SiftResult.FINISHED;
			if (state == State.BEGIN) {
				if (cp == '\"') {
					this.state = State.ACTIVE;
					this.encountered++;
					return SiftResult.ACCEPTED;
				}
				return SiftResult.REJCECTED;
			}
			if (!escaped) {
				if (cp == '\\') {
					escaped = true;
					return SiftResult.ACCEPTED;
				}
				if (cp == '\"') {
					this.state = State.ENDED;
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
		public void pushOnto(Stack<Symbol> tokenStack) {
			Symbol s = new Symbol();
			s.type = SymbolType.CONSTANT;
			Holder h;
			if (this.errors.size() == 0) {
				h = Holder.valueOf(this.string.toString());
			} else {
				h = Holder.failedComputation(new CompositeException(this.errors));
			}
			s.symbol = new Symbol.ConstantSymbol(new Constant(h));
			tokenStack.push(s);
		}

		@Override
		public SiftResult endOfStream() {
			return state == State.ENDED ? SiftResult.REJCECTED : SiftResult.FINISHED;
		}
	}

	public static class IntegerConstant implements IBasicSequence {
		private static enum State {
			START, POSTSIGN, NUMBER;
		}

		private int base = 10;
		private int length = 0;
		private State state = State.START;
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
					return SiftResult.FINISHED;
				}
			}
			if (state != State.NUMBER)
				return SiftResult.REJCECTED;
			if (Character.digit(cp, base) != -1) {
				length++;
				string.appendCodePoint(cp);
				return SiftResult.ACCEPTED;
			}
			if (length > 0) {
				return SiftResult.FINISHED;
			}
			return SiftResult.REJCECTED;
		}

		@Override
		public SiftResult endOfStream() {
			return length > 0 ? SiftResult.FINISHED : SiftResult.REJCECTED;
		}

		@Override
		public void reset() {
			base = 10;
			length = 0;
			state = State.START;
			string.setLength(0);
		}

		@Override
		public void pushOnto(Stack<Symbol> tokenStack) {
			Symbol s = new Symbol();
			s.type = SymbolType.CONSTANT;
			Holder h;
			try {
				int val = Integer.parseInt(string.toString(), base);
				h = Holder.valueOf(val);
			} catch (NumberFormatException nfe) {
				h = Holder.failedComputation(nfe);
			}
			s.symbol = new Symbol.ConstantSymbol(new Constant(h));
			tokenStack.push(s);
		}

	}
}
