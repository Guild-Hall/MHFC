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
		getWeaponDescription(MHFCWeaponClassingHelper.longswordname);
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
		if (firetype)//a pre test knowledge of particles.

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

  
         for (int i = 0; i < 10; i++) {
          par3.worldObj.spawnParticle("enchantmenttable", par3.posX, par3.posY, par3.posZ, itemRand.nextGaussian(), itemRand.nextGaussian(), itemRand.nextGaussian());
         }
        Random random = new Random();
         Vec3 look = par3.getLookVec();
        double posX = par3.posX + look.xCoord * 1.0D;
        double posY = par3.posY + look.yCoord * 1.0D;
         double posZ = par3.posZ + look.zCoord * 1.0D;
         double X = look.xCoord * 0.1D;
         double Y = look.yCoord * 0.1D;
         double Z = look.zCoord * 0.1D;
         
 
       for (int i = 0; i < 100; i++) {
         for (int z = 0; z < 10; z++) {
          par3.worldObj.spawnParticle("lava", posX + X * 1.1D, posY + Y * 1.1D, posZ + Z * 1.1D, random.nextInt(10) / 10 - 0.2D, random.nextInt(10) / 10 - 0.2D, random.nextInt(10) / 10 - 0.2D);
        }
     par3.worldObj.spawnParticle("lava", posX + X * 1.1D, posY + Y * 1.1D, posZ + Z * 1.1D, random.nextInt(10) / 10 - 0.2D, random.nextInt(10) / 10 - 0.2D, random.nextInt(10) / 10 - 0.2D);
         }
         par3.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, (int)par3.posX, (int)par3.posY, (int)par3.posZ, 0);
      
      }
     
     return false;
   }
}
