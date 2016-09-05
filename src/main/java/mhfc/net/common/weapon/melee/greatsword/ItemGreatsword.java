package mhfc.net.common.weapon.melee.greatsword;

import java.util.function.Consumer;

import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.melee.ItemWeaponMelee;
import mhfc.net.common.weapon.melee.greatsword.GreatswordWeaponStats.GreatswordWeaponStatsBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemGreatsword extends ItemWeaponMelee<GreatswordWeaponStats> {
	public static ItemGreatsword build(Consumer<GreatswordWeaponStatsBuilder> config) {
		GreatswordWeaponStatsBuilder builder = new GreatswordWeaponStatsBuilder();
		config.accept(builder);
		return new ItemGreatsword(builder.build());
	}

	public ItemGreatsword(GreatswordWeaponStats stats) {
		super(stats);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return MHFCReference.weapon_greatsword_name;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity holder, int slot, boolean isHold) {
		super.onUpdate(stack, world, holder, slot, isHold);
		if (!isHold) {
			return;
		}
		if (holder instanceof EntityPlayer) {
			EntityPlayer entity = (EntityPlayer) holder;
			entity.moveEntityWithHeading(entity.moveStrafing * -0.4f, entity.moveForward * -0.4f);
			entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 2, 3));
		}
	}

}
