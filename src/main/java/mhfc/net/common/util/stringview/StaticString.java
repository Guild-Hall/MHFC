package mhfc.net.common.util.stringview;

import java.util.Objects;

public class StaticString implements Viewable {
	private final String content;

	public StaticString(String content) {
		this.content = Objects.requireNonNull(content);
	}

	@Override
	public void appendTo(StringBuilder builder) {
		builder.append(content);
	}

	@Override
	public String toString() {
		return content;
	}
}
