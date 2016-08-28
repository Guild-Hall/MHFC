package mhfc.net.common.util.stringview;

import mhfc.net.common.util.parsing.IValueHolder;

public class Viewables {
	/**
	 * Parses the string toParse to a viewable.<br>
	 * Any text between "{{" and "}}" (non-greedy) is viewed as a {@link DynamicString} with the context given. If no
	 * such part is found, context may be null, otherwise context can not be <code>null</code><br>
	 * Any text between "[[" and "]]" (non-greedy) is viewed as a {@link LocalizedString} and is getting localized at
	 * the moment of viewing.<br>
	 * Any other parts of the string is viewed as a {@link StaticString}.<br>
	 * Color and format codes that are present are being honored inside {@link StaticString}s.
	 *
	 * @param toParse
	 *            the string to parse
	 * @param context
	 *            the context to use for dynamic parts. Can only be <code>null</code> if no such parts are present
	 * @return a viewable representing the parsed string.
	 */
	public static Viewable parse(String toParse, IValueHolder context) {
		int staticContentStartIndex = 0;
		Viewable view = JoinedView.on("");

		while (staticContentStartIndex < toParse.length()) {
			int startLocalized = toParse.indexOf("[[", staticContentStartIndex);
			int startDynamic = toParse.indexOf("{{", staticContentStartIndex);
			if (startDynamic == -1 && startLocalized == -1) {
				view = view.concat(new StaticString(toParse.substring(staticContentStartIndex)));
				break;
			}
			boolean specialIsDynamic = startLocalized == -1 || startDynamic != -1 && startDynamic < startLocalized;

			int specialStartIndex = specialIsDynamic ? startDynamic : startLocalized;
			String endSequence = specialIsDynamic ? "}}" : "]]";
			int specialEndIndex = toParse.indexOf(endSequence, specialStartIndex);
			if (specialEndIndex == -1) {
				throw new IllegalArgumentException("Parsed string contains unmatched opening brackets");
			}

			String precedingStaticPart = toParse.substring(staticContentStartIndex, specialStartIndex);
			view = view.concat(new StaticString(precedingStaticPart));

			String specialPart = toParse.substring(specialStartIndex + 2, specialEndIndex);
			Viewable specialView = specialIsDynamic ? parseDynamic(specialPart, context) : parseLocalized(specialPart);
			view = view.concat(specialView);

			staticContentStartIndex = specialEndIndex + 2;
		}
		return view;
	}

	private static Viewable parseDynamic(String dynamicPart, IValueHolder context) {
		return new DynamicString(dynamicPart, context);
	}

	private static Viewable parseLocalized(String localizedPart) {
		return new LocalizedString(localizedPart);
	}

}
