package mhfc.net.common.weapon.melee.huntinghorn;

import mhfc.net.common.helper.MHFCWeaponClassingHelper;
import mhfc.net.common.util.Cooldown;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.ComponentMelee;
import mhfc.net.common.weapon.ComponentMelee.WeaponSpecs;
import mhfc.net.common.weapon.melee.WeaponMelee;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class HuntingHornClass extends WeaponMelee {

	protected int cooldown;
	protected PotionEffect g;

	public HuntingHornClass(ToolMaterial getType, int Cooldown) {
		super(new ComponentMelee(WeaponSpecs.HUNTINGHORN, getType));
		labelWeaponClass(MHFCWeaponClassingHelper.huntinghornname);
		setTextureName(MHFCReference.weapon_hh_default_icon);
		getWeaponTable(-6, 8, 1);
		cooldown = Cooldown;
	}

	@Override
	public void onUpdate(ItemStack iStack, World world, Entity entity, int i,
			boolean flag) {
		if (!world.isRemote) {
			Cooldown.onUpdate(iStack, cooldown);
		}
		meleecomp.onUpdate(iStack, world, entity, i, flag);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target,
			EntityLivingBase player) {
		if (poisontype)
			target.addPotionEffect(new PotionEffect(Potion.poison.id, 10,
					amplified));
		if (firetype)
			target.setFire(1);
		meleecomp.hitEntity(stack, target, player);
		return true;
	}
}
