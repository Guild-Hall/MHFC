package mhfc.net.common.item.weapon.type;

import mhfc.net.MHFCMain;
import net.minecraft.item.ItemSword;

public class SemiLethalClass extends ItemSword {

	public SemiLethalClass(ToolMaterial getType) {
		super(getType);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

}
