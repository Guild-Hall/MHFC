package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponClassingHelper;
import mhfc.net.common.util.Cooldown;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.ComponentMelee;
import mhfc.net.common.weapon.ComponentMelee.WeaponSpecs;
import mhfc.net.common.weapon.melee.WeaponMelee;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class HammerClass extends WeaponMelee {

	protected int cooldown;
	protected double motY = 0.2D;
	protected int chance = 60, stunDur = 240, hitchance = 35;

	public HammerClass(ToolMaterial getType) {
		super(new ComponentMelee(WeaponSpecs.HAMMER, getType));

		getWeaponDescription(MHFCWeaponClassingHelper.hammername);
		setTextureName(MHFCReference.weapon_hm_default_icon);
		getWeaponTable(-5, 7, 1);
	}

	@Override
	public void onUpdate(ItemStack iStack, World world, Entity entity, int i,
			boolean flag) {
		if (!world.isRemote) {
			Cooldown.onUpdate(iStack, cooldown);
		}
		weapon.onUpdate(iStack, world, entity, i, flag);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target,
			EntityLivingBase player) {
		if (poisontype)
			target.addPotionEffect(new PotionEffect(Potion.poison.id, 30,
					amplified));
		if (firetype)
			target.setFire(1);
		if (Item.itemRand.nextInt() == hitchance)
			target.addPotionEffect(new PotionEffect(Potion.poison.id, 100,
					amplified));
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
