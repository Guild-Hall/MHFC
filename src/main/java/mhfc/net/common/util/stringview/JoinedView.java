package mhfc.net.common.util.stringview;

import java.util.ArrayList;
import java.util.Objects;

public class JoinedView implements Viewable {
	private ArrayList<Viewable> parts;
	private String seperator;

	private JoinedView(String on, ArrayList<Viewable> list) {
		this.parts = list; // No defensive copy
		this.seperator = Objects.requireNonNull(on);
	}

	@Override
	public JoinedView concat(Viewable view) {
		ArrayList<Viewable> newParts = new ArrayList<>(parts);
		newParts.add(Objects.requireNonNull(view));
		return new JoinedView(seperator, newParts);
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

	public static JoinedView on(String seperator) {
		return new JoinedView(seperator, new ArrayList<>());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Viewable view : parts) {
			builder.append(view.toString());
		}
		return builder.toString();
	}
}
