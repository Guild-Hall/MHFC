package mhfc.net.common.item.armor;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.index.armor.Material;
import mhfc.net.common.index.armor.Model;
import mhfc.net.common.item.ItemRarity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VangisArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_deviljho_helm_name,
			ResourceInterface.armor_deviljho_chest_name, ResourceInterface.armor_deviljho_legs_name,
			ResourceInterface.armor_deviljho_boots_name };

	public VangisArmor(EntityEquipmentSlot type) {
		super(Material.deviljho, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		switch (armorSlot) {
		case HEAD:
			return Model.deviljho;
		case LEGS:
			return null;
		case FEET:
			return Model.deviljho;
		case CHEST:
			return Model.deviljho;
		default:
			break;

		}

		return null;
	}
}
