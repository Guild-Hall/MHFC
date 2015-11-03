package mhfc.net.common.util.parsing;

import java.util.regex.Pattern;

import scala.NotImplementedError;

public class ExpressionTranslator {
	private static final Pattern commentPattern = Pattern.compile("/\\*.*?\\*/");
	private static final Pattern whitespace = Pattern.compile("\\s*");

	public static IExpression parse(String expression) throws IllegalArgumentException {
		// Preprocessed: all Character.isWhitespace() is replaced by ' '
		String stage1 = expression.codePoints().map(i -> Character.isWhitespace(i) ? ' ' : i)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		// Replace all comments
		String stage2 = commentPattern.matcher(stage1).replaceAll(" ");
		// Collapse whitespace and trim
		String stage3 = whitespace.matcher(stage2).replaceAll(" ");
		return parse(stage3);
	}

	private static IExpression parseCleaned(String cleanExpression) {
		// Cleaned as in: does only contain spaces as whitespace, doesn't
		// contain any comments and only one sequential whitespace.
		// Can't be avoided...
		String trimmed = cleanExpression.trim();

		throw new NotImplementedError();
	}
}
