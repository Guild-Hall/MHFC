package mhfc.net.common.weapon.melee.greatsword;

import mhfc.net.common.helper.MHFCWeaponClassingHelper;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.ComponentMelee;
import mhfc.net.common.weapon.ComponentMelee.WeaponSpecs;
import mhfc.net.common.weapon.melee.WeaponMelee;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class GreatswordClass extends WeaponMelee {

	public GreatswordClass(ToolMaterial getType) {
		super(new ComponentMelee(WeaponSpecs.GREATSWORD, getType));
		labelWeaponClass(MHFCWeaponClassingHelper.greatswordname);
		setTextureName(MHFCReference.weapon_gs_default_icon);
		getWeaponTable(-3, 3, 1);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target,
			EntityLivingBase player) {
		if (poisontype)
			target.addPotionEffect(new PotionEffect(Potion.poison.id, 30,
					amplified));
		if (firetype)
			target.setFire(1);
		return true;
	}
	
	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity par3Entity,
			int i, boolean flag) {
		 if ((par3Entity instanceof EntityPlayer)) {
			 EntityPlayer entity = (EntityPlayer)par3Entity;
			 if (entity.getCurrentEquippedItem() == itemstack) {
				 
				 entity.moveEntityWithHeading(entity.moveStrafing*-0.4f, entity.moveForward *-0.4f);
				 entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 2, 3));
			 }
		 }
		 }

}
