package mhfc.heltrato.common.item.weapon.bowgun;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.item.Item;

public class WeaponBGLSpartacusFire extends Item{
	
	public WeaponBGLSpartacusFire(){
		super();
		setCreativeTab(MHFCMain.mhfctabs);
		setFull3D();
		maxStackSize = 1;
		setUnlocalizedName(MHFCReference.weapon_bgl_spartacusfire_name);
	}

}
