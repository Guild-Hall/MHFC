package mhfc.net.common.weapon.range.bowgun.heavy;

import java.util.function.Consumer;

import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.range.bowgun.BowgunWeaponStats;
import mhfc.net.common.weapon.range.bowgun.BowgunWeaponStats.BowgunWeaponStatsBuilder;
import mhfc.net.common.weapon.range.bowgun.ItemBowgun;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemHeavyBowgun extends ItemBowgun {
	public static ItemHeavyBowgun build(Consumer<BowgunWeaponStatsBuilder> config) {
		BowgunWeaponStatsBuilder builder = new BowgunWeaponStatsBuilder();
		config.accept(builder);
		return new ItemHeavyBowgun(builder.build());
	}

	public ItemHeavyBowgun(BowgunWeaponStats stats) {
		super(stats);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return MHFCReference.weapon_heavybowgun_name;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		return new ActionResult<>(EnumActionResult.PASS, stack);
	}
}
