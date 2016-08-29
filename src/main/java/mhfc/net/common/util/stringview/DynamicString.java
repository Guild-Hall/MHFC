package mhfc.net.common.util.stringview;

import java.util.Objects;

import mhfc.net.common.util.parsing.ExpressionTranslator;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

public class DynamicString implements Viewable {
	private final IValueHolder value;
	private static ExpressionTranslator translator = new ExpressionTranslator();

	public DynamicString(IValueHolder value) {
		this.value = Objects.requireNonNull(value);
	}

	public DynamicString(String toParse, IValueHolder context) {
		this(translator.parse(toParse, context));
	}

	@Override
	public void appendTo(StringBuilder builder) {
		String append = Holder.snapshotSafely(value).toString();
		builder.append(append);
	}

	@Override
	public String toString() {
		return "{{" + value + "}}";
	}
}
