package mhfc.net.common.weapon.melee.longsword;

import java.util.Random;

import mhfc.net.common.helper.MHFCWeaponClassingHelper;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.ComponentMelee;
import mhfc.net.common.weapon.ComponentMelee.WeaponSpecs;
import mhfc.net.common.weapon.melee.WeaponMelee;
import mhfc.net.common.weapon.melee.iWeaponReach;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class LongswordClass extends WeaponMelee implements iWeaponReach {

	public String lsLocal = "longsword_";

	public LongswordClass(ToolMaterial getType) {
		super(new ComponentMelee(WeaponSpecs.LONGSWORD, getType));
		labelWeaponClass(MHFCWeaponClassingHelper.longswordname);
		setTextureName(MHFCReference.weapon_ls_default_icon);
		getWeaponTable(4, -12, 0);

	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase ent,
			EntityLivingBase par3) {
		// player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30, 1));

		if (poisontype)
			ent.addPotionEffect(new PotionEffect(Potion.poison.id, 30,
					amplified));

		meleecomp.hitEntity(stack, ent, par3);
		return true;
	}

	@Override
	public float getExtendedReach(World world, EntityLivingBase living, ItemStack itemstack) {
		return 6;
	}
	
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
     if ((entityLiving instanceof EntityPlayer)) {
       EntityPlayer par3 = (EntityPlayer)entityLiving;
         for (int i = 0; i < 6; i++) {
        	if(firetype)par3.worldObj.spawnParticle("lava", par3.posX, par3.posY, par3.posZ, itemRand.nextGaussian(), itemRand.nextGaussian(), itemRand.nextGaussian());
         }
    
      }
     
     return false;
   }
}
