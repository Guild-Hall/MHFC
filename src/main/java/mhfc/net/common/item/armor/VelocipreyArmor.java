package mhfc.net.common.item.armor;

import mhfc.net.common.index.ArmorMaterials;
import mhfc.net.common.index.ArmorModels;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.ItemRarity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VelocipreyArmor extends ItemArmorMHFC {
	private static final String[] names = { ResourceInterface.armor_velociprey_helm_name,
			ResourceInterface.armor_velociprey_chest_name, ResourceInterface.armor_velociprey_legs_name,
			ResourceInterface.armor_velociprey_boots_name };

	public VelocipreyArmor(EntityEquipmentSlot type) {
		super(ArmorMaterials.ArmorVelociprey, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModels.velociprey;
	}

}
