package mhfc.net.common.weapon.melee.huntinghorn;

import java.util.function.Consumer;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.melee.ItemWeaponMelee;
import mhfc.net.common.weapon.melee.huntinghorn.HuntingHornWeaponStats.HuntingHornWeaponStatsBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemHuntingHorn extends ItemWeaponMelee<HuntingHornWeaponStats> {
	public static ItemHuntingHorn build(Consumer<HuntingHornWeaponStatsBuilder> config) {
		HuntingHornWeaponStatsBuilder builder = new HuntingHornWeaponStatsBuilder();
		config.accept(builder);
		return new ItemHuntingHorn(builder.build());
	}

	public ItemHuntingHorn(HuntingHornWeaponStats stats) {
		super(stats);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return MHFCReference.weapon_huntinghorn_name;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		ItemStack newstack = super.onItemRightClick(stack, world, player);
		stats.toggleNote(stack);
		return newstack;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int useCounter) {
		super.onUsingTick(stack, player, useCounter);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		boolean cancel = super.onEntitySwing(entityLiving, stack);
		if (cancel) {
			return true;
		}
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			Note current = stats.getCurrentNote(stack);
			stats.onNotePlayed(stack, player, current);
		}
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase player) {
		boolean superResult = super.hitEntity(stack, target, player);
		if (!isOffCooldown(stack)) {
			return superResult;
		}
		target.addPotionEffect(new PotionEffect(MHFCPotionRegistry.stun.id, 10, 5));
		triggerCooldown(stack);
		return true;
	}
}
