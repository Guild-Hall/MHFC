package mhfc.net.common.util.parsing.syntax;

import java.util.Stack;

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
}
