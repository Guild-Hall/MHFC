package mhfc.net.common.item.armor;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.index.armor.Material;
import mhfc.net.common.index.armor.Model;
import mhfc.net.common.item.ItemRarity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NibelsnarfArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_nibelsnarf_helm_name,
			ResourceInterface.armor_nibelsnarf_chest_name, ResourceInterface.armor_nibelsnarf_legs_name,
			ResourceInterface.armor_nibelsnarf_boots_name };

	public NibelsnarfArmor(EntityEquipmentSlot type) {
		super(Material.nibelsnarf, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		switch (armorSlot) {
		case HEAD:
			return Model.nibelsnarf;
		case LEGS:
			return null;
		case FEET:
			return Model.nibelsnarf;
		case CHEST:
			return Model.nibelsnarf;
		default:
			break;
		}

		return null;
	}

}
