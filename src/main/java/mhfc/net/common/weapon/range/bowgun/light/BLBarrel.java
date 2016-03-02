package mhfc.net.common.weapon.range.bowgun.light;

import mhfc.net.common.entity.projectile.EntityBullet;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BLBarrel extends LightBowgun {

	public BLBarrel() {
		super();
	
		damage = 12;
		
		setUnlocalizedName(MHFCReference.weapon_bgl_barrel_name);
		getWeaponDescription("No Element", 1);
	}
	
	

}
