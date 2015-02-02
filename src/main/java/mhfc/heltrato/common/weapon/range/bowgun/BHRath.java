package mhfc.heltrato.common.weapon.range.bowgun;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.core.registry.MHFCRegItem;
import mhfc.heltrato.common.entity.projectile.EntityBullet;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BHRath extends Item {
	
	public BHRath() {
		super();
		setUnlocalizedName(MHFCReference.weapon_bgh_rath_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	


}
