package mhfc.net.common.util.parsing.syntax;

import java.util.Objects;
import java.util.PrimitiveIterator.OfInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mhfc.net.common.util.parsing.syntax.ISyntaxRule.ISyntaxRuleInstance;

public class BasicSequences {
	/**
	 * @see Double#valueOf(String)
	 */
	private static final String Digits = "(\\p{Digit}+)";
	private static final String HexDigits = "(\\p{XDigit}+)";
	private static final String Exp = "[eE][+-]?" + Digits;
	private static final String fpRegex = ("[+-]?(" + // Optional sign character
			"NaN|" + "Infinity|" +
			// Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
			// Note: to differ between integers and floating points, we use
			// Digits . Digits_opt ExponentPart_opt FloatTypeSuffix_opt
	"(((" + Digits + "(\\.)(" + Digits + "?)(" + Exp + ")?)|" +
			// . Digits ExponentPart_opt FloatTypeSuffix_opt
			"(\\.(" + Digits + ")(" + Exp + ")?)|" +
			// Hexadecimal strings
			"((" +
			// 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
			"(0[xX]" + HexDigits + "(\\.)?)|" +
			// 0[xX] HexDigits_opt . HexDigits BinaryExponent
			// FloatTypeSuffix_opt
	"(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" + ")[pP][+-]?" + Digits + "))" + "[fFdD]?))");

	public static class RawMatch {
		private static final RawMatch NO_MATCH = new RawMatch(null, 0);

		private final String rawMatch;
		private final int codepointsMatched;

		private RawMatch(String parsed, int codepoints) {
			this.rawMatch = codepoints <= 0 ? null : Objects.requireNonNull(parsed);
			this.codepointsMatched = codepoints;
		}

		public String get() {
			return this.rawMatch;
		}

		public int amountCPMatched() {
			return this.codepointsMatched;
		}
	}

	public static abstract class SyntaxMatch implements ISyntaxRuleInstance {
		protected RawMatch match;

		protected abstract RawMatch parse(CharSequence toParse);

		@Override
		public int match(CharSequence toParse) {
			this.match = parse(toParse);
			return this.match.amountCPMatched();
		}

		public RawMatch getRaw() {
			return this.match;
		}
	}

	public static RawMatch matchIdentifier(CharSequence sequence) {
		OfInt codepointIt = sequence.codePoints().iterator();
		if (!codepointIt.hasNext())
			return RawMatch.NO_MATCH;
		int firstCP = codepointIt.nextInt();
		if (!Character.isJavaIdentifierStart(firstCP))
			return RawMatch.NO_MATCH;

		StringBuilder identifierBuilder = new StringBuilder();
		identifierBuilder.appendCodePoint(firstCP);
		int cpCount = 1;
		for (; codepointIt.hasNext();) {
			int cp = codepointIt.nextInt();
			if (!Character.isJavaIdentifierPart(cp))
				break;
			identifierBuilder.appendCodePoint(cp);
			cpCount++;
		}
		return new RawMatch(identifierBuilder.toString(), cpCount);
	}

	public static class IdentifierMatch extends SyntaxMatch {
		public static final ISyntaxRule<IdentifierMatch> factory = IdentifierMatch::new;

		@Override
		protected RawMatch parse(CharSequence toParse) {
			return matchIdentifier(toParse);
		}
	}

	// http://stackoverflow.com/questions/18547501/regex-to-replace-all-string-literals-in-a-java-file
	private static final Pattern stringLiteralRegex = Pattern.compile("^\"((?:\\\\\"|[^\"\n])*?)\"");

	public static RawMatch matchStringConstant(CharSequence sequence) {
		Matcher match = stringLiteralRegex.matcher(sequence);
		if (!match.find()) {
			return RawMatch.NO_MATCH;
		}
		String rawComment = match.group(1);
		return new RawMatch(rawComment, 2 + rawComment.codePointCount(0, rawComment.length()));
	}

	public static class StringMatch extends SyntaxMatch {
		public static final ISyntaxRule<StringMatch> factory = StringMatch::new;

		@Override
		protected RawMatch parse(CharSequence toParse) {
			return matchStringConstant(toParse);
		}
	}

	private static final Pattern floatingPointRegex = Pattern.compile("^" + fpRegex);

	public static RawMatch matchFloatConstant(CharSequence sequence) {
		Matcher match = floatingPointRegex.matcher(sequence);
		if (!match.find()) {
			return RawMatch.NO_MATCH;
		}
		String rawParsed = match.group(0);
		return new RawMatch(rawParsed, rawParsed.codePointCount(0, rawParsed.length()));
	}

	public static class FloatMatch extends SyntaxMatch {
		public static final ISyntaxRule<FloatMatch> factory = FloatMatch::new;

		@Override
		protected RawMatch parse(CharSequence toParse) {
			return matchFloatConstant(toParse);
		}
	}

	private static final Pattern integerRegex = Pattern.compile("^[-+]?(0|[1-9][0-9]*)");

	public static RawMatch matchIntConstant(CharSequence sequence) {
		Matcher match = integerRegex.matcher(sequence);
		if (!match.find()) {
			return RawMatch.NO_MATCH;
		}
		String rawParsed = match.group(0);
		return new RawMatch(rawParsed, rawParsed.codePointCount(0, rawParsed.length()));
	}

	public static class IntMatch extends SyntaxMatch {
		public static final ISyntaxRule<IntMatch> factory = IntMatch::new;

		@Override
		protected RawMatch parse(CharSequence toParse) {
			return matchIntConstant(toParse);
		}
	}
}
