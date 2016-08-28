package mhfc.net.common.util.stringview;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JoiningView implements Viewable {
	private List<Viewable> parts = new ArrayList<>();
	private String seperator;

	public JoiningView(String on) {
		this.seperator = Objects.requireNonNull(on);
	}

	@Override
	public JoiningView append(Viewable view) {
		this.parts.add(Objects.requireNonNull(view));
		return this;
	}

	@Override
	public void appendTo(StringBuilder builder) {
		boolean first = true;
		for (Viewable view : parts) {
			if (!first) {
				builder.append(seperator);
			}
			view.appendTo(builder);
			first = false;
		}
	};

	public static JoiningView on(String seperator) {
		return new JoiningView(seperator);
	}
}
