package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.helper.MHFCWeaponClassingHelper;
import mhfc.net.common.util.Cooldown;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.ComponentMelee;
import mhfc.net.common.weapon.ComponentMelee.WeaponSpecs;
import mhfc.net.common.weapon.melee.WeaponMelee;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class HammerClass extends WeaponMelee {

	protected static int cooldown;
	protected double motY = 0.2D;
	protected int stunDur = 120;
	protected int critDamageFlat = 20;  // Hammer deals FLAT. 

	public HammerClass(ToolMaterial getType, int stunCooldown) {
		super(new ComponentMelee(WeaponSpecs.HAMMER, getType));
		cooldown = stunCooldown;
		labelWeaponClass(MHFCWeaponClassingHelper.hammername);
		setTextureName(MHFCReference.weapon_hm_default_icon);
		getWeaponTable(-5, 7, 5);
	}

	@Override
	public void onUpdate(ItemStack iStack, World world, Entity par3Entity, int i,
			boolean flag) {
		
		if (!world.isRemote) {
			Cooldown.onUpdate(iStack, cooldown);
		}
		
		if ((par3Entity instanceof EntityPlayer)) {
			 EntityPlayer entity = (EntityPlayer)par3Entity;
			 if (entity.getCurrentEquippedItem() == iStack) {
				 entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 100, 3));
			 
			 }
		 }
		meleecomp.onUpdate(iStack, world, par3Entity, i, flag);
		
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target,
			EntityLivingBase player) {
		if (Cooldown.canUse(stack, cooldown)) {
			target.addPotionEffect(new PotionEffect(MHFCPotionRegistry.stun.id, stunDur, 5));
		}
		
		return true;
	}
	
	

	/*
	 * @Override public ItemStack onItemRightClick(ItemStack iStack, World
	 * world, EntityPlayer player) { weapon.onItemRightClick(iStack, world,
	 * player); if (Cooldown.canUse(iStack, cooldown)) {
	 * 
	 * @SuppressWarnings("unchecked") List<Entity> list = player.worldObj
	 * .getEntitiesWithinAABBExcludingEntity(player.getLastAttacker(),
	 * player.boundingBox.expand(12D, 8D, 12D)); list.remove(player); for
	 * (Entity entity : list) { if(entity != null){ entity.motionY += motY;
	 * if(rand.nextInt() == chance){ ((EntityLivingBase) entity)
	 * .addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, stunDur, 6)); }
	 * } } } // player.motionY++; return iStack; }
	 */

}
