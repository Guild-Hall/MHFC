package mhfc.heltrato.common.item.weapon.bowgun;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.item.Item;

public class WeaponBGLShooterBarrel extends Item{
	
	public WeaponBGLShooterBarrel(){
		super();
		setCreativeTab(MHFCMain.mhfctabs);
		setFull3D();
		maxStackSize = 1;
		setUnlocalizedName(MHFCReference.weapon_bgl_shooterbarrel_name);
	}

}
