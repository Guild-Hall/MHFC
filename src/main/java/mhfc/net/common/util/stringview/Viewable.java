package mhfc.net.common.util.stringview;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface Viewable {
	void appendTo(StringBuilder builder);

	default Viewable concat(Viewable other) {
		return JoinedView.on("").concat(this).concat(other);
	}

	default Viewable appendStatic(String staticString) {
		return concat(new StaticString(staticString));
	}
}
