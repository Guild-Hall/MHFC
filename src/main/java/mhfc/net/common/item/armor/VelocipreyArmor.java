package mhfc.net.common.item.armor;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.index.armor.Material;
import mhfc.net.common.index.armor.Model;
import mhfc.net.common.item.ItemRarity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VelocipreyArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_velociprey_helm_name,
			ResourceInterface.armor_velociprey_chest_name, ResourceInterface.armor_velociprey_legs_name,
			ResourceInterface.armor_velociprey_boots_name };

	public VelocipreyArmor(EntityEquipmentSlot type) {
		super(Material.velociprey, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		switch (armorSlot) {
		case HEAD:
			return Model.velociprey;
		case LEGS:
			return null;
		case FEET:
			return Model.velociprey;
		case CHEST:
			return Model.velociprey;
		default:
			break;

		}

		return null;
	}
}
