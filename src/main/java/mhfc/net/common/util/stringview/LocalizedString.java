package mhfc.net.common.util.stringview;

import java.util.Objects;

import net.minecraft.util.StatCollector;

public class LocalizedString implements Viewable {
	private String toLocalize;

	public LocalizedString(String toLocalize) {
		this.toLocalize = Objects.requireNonNull(toLocalize);
	}

	@Override
	public void appendTo(StringBuilder builder) {
		builder.append(StatCollector.translateToLocal(toLocalize));
	}

	@Override
	public String toString() {
		return "[[" + toLocalize + "]]";
	}

}
