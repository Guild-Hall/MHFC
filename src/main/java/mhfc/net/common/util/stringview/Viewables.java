package mhfc.net.common.util.stringview;

import mhfc.net.common.util.parsing.IValueHolder;

public class Viewables {
	public static Viewable parse(String toParse, IValueHolder context) {
		int staticContentStartIndex = 0;
		Viewable view = JoiningView.on(toParse);

		while (staticContentStartIndex < toParse.length()) {
			int startLocalized = toParse.indexOf("[[", staticContentStartIndex);
			int startDynamic = toParse.indexOf("{{", staticContentStartIndex);
			if (startDynamic == -1 && startLocalized == -1) {
				view = view.append(new StaticString(toParse.substring(staticContentStartIndex)));
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
			view = view.append(new StaticString(precedingStaticPart));

			String specialPart = toParse.substring(specialStartIndex + 2, specialEndIndex);
			Viewable specialView = specialIsDynamic ? parseDynamic(specialPart, context) : parseLocalized(specialPart);
			view = view.append(specialView);

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
