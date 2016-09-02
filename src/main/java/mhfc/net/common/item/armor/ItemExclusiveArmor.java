package mhfc.net.common.item.armor;

import java.util.Map;
import java.util.Objects;

import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.system.Privilege;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemExclusiveArmor extends ItemArmorMHFC {
	private final Privilege requiredPrivilege;

	public ItemExclusiveArmor(
			Privilege requirement,
			ArmorMaterial armor,
			ItemRarity rarity,
			EntityEquipmentSlot armorType,
			Map<EntityEquipmentSlot, String> slotToTexture,
			Map<EntityEquipmentSlot, ModelBiped> slotToModel) {

		super(armor, rarity, armorType, slotToTexture, slotToModel);
		this.requiredPrivilege = Objects.requireNonNull(requirement);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (!(entity instanceof EntityPlayer && checkPrivilege((EntityPlayer) entity))) {
			return MHFCReference.armor_null_tex;
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
