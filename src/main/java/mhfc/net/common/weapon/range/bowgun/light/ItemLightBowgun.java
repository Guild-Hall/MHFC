package mhfc.net.common.weapon.range.bowgun.light;

import java.util.function.Consumer;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.weapon.range.bowgun.BowgunWeaponStats;
import mhfc.net.common.weapon.range.bowgun.BowgunWeaponStats.BowgunWeaponStatsBuilder;
import mhfc.net.common.weapon.range.bowgun.ItemBowgun;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemLightBowgun extends ItemBowgun {
	public static ItemLightBowgun build(Consumer<BowgunWeaponStatsBuilder> config) {
		BowgunWeaponStatsBuilder builder = new BowgunWeaponStatsBuilder();
		config.accept(builder);
		return new ItemLightBowgun(builder.build());
	}

	public ItemLightBowgun(BowgunWeaponStats stats) {
		super(stats);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return ResourceInterface.weapon_lightbowgun_name;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		return new ActionResult<>(EnumActionResult.PASS, stack);
	}
}
