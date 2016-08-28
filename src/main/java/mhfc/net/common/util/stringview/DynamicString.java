package mhfc.net.common.util.stringview;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mhfc.net.common.util.parsing.ExpressionTranslator;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

public class DynamicString implements Viewable {

	private static class StaticString implements Viewable {
		private final String content;

		public StaticString(String content) {
			this.content = Objects.requireNonNull(content);
		}

		@Override
		public void appendTo(StringBuilder builder) {
			builder.append(content);
		}
	}

	private static class DynamicPart implements Viewable {
		private final IValueHolder value;

		public DynamicPart(IValueHolder value) {
			this.value = Objects.requireNonNull(value);
		}

		@Override
		public void appendTo(StringBuilder builder) {
			String append = Holder.snapshotSafely(value).toString();
			builder.append(append);
		}
	}

	private static class NestedPart implements Viewable {
		private final Viewable content;

		public NestedPart(Viewable content) {
			this.content = Objects.requireNonNull(content);
		}

		@Override
		public void appendTo(StringBuilder builder) {
			content.appendTo(builder);
		}
	}

	private static Pattern dynamicContentPattern = Pattern.compile("\\{\\{(([^\\}]|\\}[^\\}])*)\\}\\}");

	private List<Viewable> stringViews = new ArrayList<>();
	private ExpressionTranslator translator = new ExpressionTranslator();

	public DynamicString() {}

	public DynamicString append(String toParse, IValueHolder context) {
		Matcher match = dynamicContentPattern.matcher(toParse);
		int staticContentStartIndex = 0;

		while (match.find()) {
			int staticContentEndIndex = match.start();
			stringViews.add(new StaticString(toParse.substring(staticContentStartIndex, staticContentEndIndex)));
			String dynamic = match.group(1);
			IValueHolder parsed = translator.parse(dynamic, context);
			stringViews.add(new DynamicPart(parsed));
			staticContentStartIndex = match.end();
		}

		if (staticContentStartIndex < toParse.length()) {
			stringViews.add(new StaticString(toParse.substring(staticContentStartIndex)));
		}

		return this;
	}

	public DynamicString appendStatic(String staticString) {
		stringViews.add(new StaticString(staticString));
		return this;
	}

	public DynamicString append(Viewable other) {
		stringViews.add(new NestedPart(other));
		return this;
	}

	@Override
	public void appendTo(StringBuilder builder) {
		stringViews.stream().forEach(p -> p.appendTo(builder));
	}
}
