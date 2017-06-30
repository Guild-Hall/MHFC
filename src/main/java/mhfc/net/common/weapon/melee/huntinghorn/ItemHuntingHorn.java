package mhfc.net.common.weapon.melee.huntinghorn;

import java.util.function.Consumer;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.weapon.melee.ItemWeaponMelee;
import mhfc.net.common.weapon.melee.huntinghorn.HuntingHornWeaponStats.HuntingHornWeaponStatsBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
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
		return ResourceInterface.weapon_huntinghorn_name;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ActionResult<ItemStack> newstack = super.onItemRightClick(world, player, hand);
		ItemStack stack = player.getHeldItem(hand);
		stats.toggleNote(stack);
		return ActionResult.newResult(EnumActionResult.SUCCESS, newstack.getResult());
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int useCounter) {
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
	public void onUpdate(ItemStack stack, World world, Entity holder, int slot, boolean isHoldItem) {
		super.onUpdate(stack, world, holder, slot, isHoldItem);
		if (!isHoldItem) {
			return;
		}
		if (holder instanceof EntityPlayer) {
			EntityPlayer entity = (EntityPlayer) holder;
			entity.moveEntityWithHeading(entity.moveStrafing * -0.1f, entity.moveForward * -0.1f);
		}
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase player) {
		boolean superResult = super.hitEntity(stack, target, player);
		if (!isOffCooldown(stack)) {
			return superResult;
		}
		target.addPotionEffect(new PotionEffect(MHFCPotionRegistry.getRegistry().stun, 10, 5));
		triggerCooldown(stack);
		return true;
	}
}
