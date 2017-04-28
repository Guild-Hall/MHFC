package mhfc.net.common.item.armor;

import java.util.Map;
import java.util.Objects;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.system.Privilege;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

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
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		EntityPlayer player = (EntityPlayer)entity;
		if (stack != null && !checkPrivilege(player)) {
			return null;
		}
		
		return super.getArmorTexture(stack, entity, slot, type);
	}


	protected boolean checkPrivilege(EntityPlayer player) {
		return requiredPrivilege.hasPrivilege(player);
	}

	@Override
	protected boolean shouldApplySetBonus(EntityPlayer player) {
		return checkPrivilege(player) && super.shouldApplySetBonus(player);
	}

}
