package mhfc.net.common.weapon.range.bowgun;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.item.Item;

public class BLBarrel extends Item {

	public BLBarrel() {
		super();
		setUnlocalizedName(MHFCReference.weapon_bgl_barrel_name);
		setTextureName(MHFCReference.weapon_bgl_barrel_icon);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
