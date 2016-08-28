package mhfc.net.common.util.stringview;

import java.util.ArrayList;
import java.util.Objects;

public class JoiningView implements Viewable {
	private ArrayList<Viewable> parts;
	private String seperator;

	private JoiningView(String on, ArrayList<Viewable> list) {
		this.parts = list; // No defensive copy
		this.seperator = Objects.requireNonNull(on);
	}

	@Override
	public JoiningView append(Viewable view) {
		ArrayList<Viewable> newParts = new ArrayList<>(parts);
		newParts.add(Objects.requireNonNull(view));
		return new JoiningView(seperator, newParts);
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
		return new JoiningView(seperator, new ArrayList<>());
	}
}
