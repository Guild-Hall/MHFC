package mhfc.net.common.weapon.melee.huntinghorn;

import java.util.function.Consumer;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.helper.MHFCWeaponClassingHelper;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.melee.ItemWeaponMelee;
import mhfc.net.common.weapon.melee.huntinghorn.HuntingHornWeaponStats.HuntingHornWeaponStatsBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class ItemHuntingHorn extends ItemWeaponMelee<HuntingHornWeaponStats> {
	public static ItemHuntingHorn build(Consumer<HuntingHornWeaponStatsBuilder> config) {
		HuntingHornWeaponStatsBuilder builder = new HuntingHornWeaponStatsBuilder();
		config.accept(builder);
		return new ItemHuntingHorn(builder.build());
	}

	public ItemHuntingHorn(HuntingHornWeaponStats stats) {
		super(stats);
		setTextureName(MHFCReference.weapon_hh_default_icon);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return MHFCWeaponClassingHelper.huntinghornname;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase player) {
		if (!isOffCooldown(stack)) {
			return false;
		}
		target.addPotionEffect(new PotionEffect(MHFCPotionRegistry.stun.id, 10, 5));
		triggerCooldown(stack);
		return true;
	}
}
