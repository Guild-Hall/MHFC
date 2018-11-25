package mhfc.net.common.item.armor;

import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.system.Privilege;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

import java.util.Map;
import java.util.Objects;

public abstract class ArmorExclusive extends ArmorBase {
	private final Privilege requiredPrivilege;

	public ArmorExclusive(
			Privilege requirement,
			ArmorMaterial armor,
			ItemRarity rarity,
			EntityEquipmentSlot armorType) {
		this(requirement, armor, rarity, armorType, null);
	}

	public ArmorExclusive(
			Privilege requirement,
			ArmorMaterial armor,
			ItemRarity rarity,
			EntityEquipmentSlot armorType,
			Map<EntityEquipmentSlot, String> slotToTexture) {
		super(armor, rarity, armorType, slotToTexture);
		this.requiredPrivilege = Objects.requireNonNull(requirement);
	}

	protected boolean checkPrivilege(EntityPlayer player) {
		return requiredPrivilege.hasPrivilege(player);
	}

	@Override
	protected boolean shouldApplySetBonus(EntityPlayer player) {
		return checkPrivilege(player) && super.shouldApplySetBonus(player);
	}

}
