package mhfc.net.common.weapon.melee.hammer;

import java.util.function.Consumer;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.melee.ItemWeaponMelee;
import mhfc.net.common.weapon.melee.hammer.HammerWeaponStats.HammerWeaponStatsBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemHammer extends ItemWeaponMelee<HammerWeaponStats> {
	public static ItemHammer build(Consumer<HammerWeaponStatsBuilder> config) {
		HammerWeaponStatsBuilder builder = new HammerWeaponStatsBuilder();
		config.accept(builder);
		return new ItemHammer(builder.build());
	}

	protected static final double motY = 0.2D;
	protected static final int stunDur = 120;
	protected static final int critDamageFlat = 20; // Hammer deals FLAT.

	public ItemHammer(HammerWeaponStats stats) {
		super(stats);
		setTextureName(MHFCReference.weapon_hm_default_icon);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return MHFCReference.weapon_hammer_name;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity holder, int slot, boolean isHold) {
		super.onUpdate(stack, world, holder, slot, isHold);
		if (!isHold) {
			return;
		}
		if (holder instanceof EntityPlayer) {
			EntityPlayer entity = (EntityPlayer) holder;
			entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 100, 3));
		}
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase player) {
		if (!isOffCooldown(stack)) {
			return false;
		}
		target.addPotionEffect(new PotionEffect(MHFCPotionRegistry.getRegistry().stun.id, stunDur, 5));
		triggerCooldown(stack);
		return true;
	}
}
