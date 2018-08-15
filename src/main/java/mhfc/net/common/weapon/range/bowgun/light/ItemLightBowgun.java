package mhfc.net.common.weapon.range.bowgun.light;

import java.util.UUID;
import java.util.function.Consumer;

import com.google.common.collect.Multimap;

import mhfc.net.common.index.AttributeModifiers;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.weapon.range.bowgun.BowgunWeaponStats;
import mhfc.net.common.weapon.range.bowgun.BowgunWeaponStats.BowgunWeaponStatsBuilder;
import mhfc.net.common.weapon.range.bowgun.ItemBowgun;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemLightBowgun extends ItemBowgun {
	protected static final UUID LIGHT_BOWGUN_EFFECT_UUID = UUID.fromString("ebf5742a-43f8-4d3c-873f-5fa7439654ff");

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

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attributeModifiers = super.getAttributeModifiers(slot, stack);
		if (slot == EntityEquipmentSlot.MAINHAND) {
			attributeModifiers.put(
					SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
					new AttributeModifier(
							LIGHT_BOWGUN_EFFECT_UUID,
							"Weapon modifier",
							-0.15f,
							AttributeModifiers.MULTIPLICATIVE));
		}
		return attributeModifiers;
	}
}
