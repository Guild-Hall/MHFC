package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.helper.MHFCWeaponClassingHelper;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.ComponentMelee;
import mhfc.net.common.weapon.ComponentMelee.WeaponSpecs;
import mhfc.net.common.weapon.melee.WeaponMelee;
import mhfc.net.common.weapon.melee.iWeaponReach;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LongswordClass extends WeaponMelee implements iWeaponReach {

	public String lsLocal = "longsword_";
	
	protected int longswordAttackUpDuration;

	public LongswordClass(ToolMaterial getType, int weaponAttackUpDuration) {
		super(new ComponentMelee(WeaponSpecs.LONGSWORD, getType));
		labelWeaponClass(MHFCWeaponClassingHelper.longswordname);
		setTextureName(MHFCReference.weapon_ls_default_icon);
		getWeaponTable(4, -12, 0);
		
		longswordAttackUpDuration = weaponAttackUpDuration;

	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase ent,EntityLivingBase par3) {
		if (counter < 60) {
			hits += 1;
			counter = 0;
		}else {
			hits = 0;
			counter = 0;
		}

		meleecomp.hitEntity(stack, ent, par3);
		return true;
	}

	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
		EntityPlayer player = (EntityPlayer)entity;
		ItemStack equipped = player.getCurrentEquippedItem();
		if (equipped == stack) {
		//	System.out.println("weapon longsword "); check if the weapon is held. it works. @heltrato
			if (this.hits > 40) {
				player.addPotionEffect(new PotionEffect(MHFCPotionRegistry.longswordattackup.id, longswordAttackUpDuration));;
			}
		}
		
		counter += 1;
		if (counter > 60) {
			hits = 0;
			counter = 0;
		}
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
	
	
	@Override
	public float getExtendedReach(World world, EntityLivingBase living, ItemStack itemstack) {
		return 6;
	}
}
