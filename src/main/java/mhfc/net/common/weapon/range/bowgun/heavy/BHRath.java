package mhfc.net.common.weapon.range.bowgun.heavy;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.item.Item;

public class BHRath extends Item {

	public BHRath() {
		super();
		setUnlocalizedName(MHFCReference.weapon_bgh_rath_name);
		setTextureName(MHFCReference.weapon_bgh_rath_icon);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
