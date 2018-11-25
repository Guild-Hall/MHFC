package mhfc.net.common.util.stringview;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

@SideOnly(Side.CLIENT)
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
