package mhfc.net.common.util.stringview;

import java.util.Objects;

import net.minecraft.client.resources.I18n;

public class LocalizedString implements Viewable {
	private String toLocalize;

	public LocalizedString(String toLocalize) {
		this.toLocalize = Objects.requireNonNull(toLocalize);
	}

	@Override
	public void appendTo(StringBuilder builder) {
		builder.append(I18n.format(toLocalize));
	}

	@Override
	public String toString() {
		return "[[" + toLocalize + "]]";
	}

}
