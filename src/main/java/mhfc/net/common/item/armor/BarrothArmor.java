package mhfc.net.common.item.armor;

import mhfc.net.common.index.ArmorMaterials;
import mhfc.net.common.index.ArmorModels;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.ItemRarity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BarrothArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_barroth_helm_name,
			ResourceInterface.armor_barroth_chest_name, ResourceInterface.armor_barroth_legs_name,
			ResourceInterface.armor_barroth_boots_name };

	public BarrothArmor(EntityEquipmentSlot type) {
		super(ArmorMaterials.ArmorBarroth, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModels.barroth;
	}
}
