package mhfc.net.common.item.armor;

import mhfc.net.common.index.ArmorMaterials;
import mhfc.net.common.index.ArmorModels;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.ItemRarity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GreatJaggiArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_greatjaggi_helm_name,
			ResourceInterface.armor_greatjaggi_chest_name, ResourceInterface.armor_greatjaggi_legs_name,
			ResourceInterface.armor_greatjaggi_boots_name };

	public GreatJaggiArmor(EntityEquipmentSlot type, int renderIndex) {
		super(ArmorMaterials.ArmorGreatJaggi, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
		renderIndex = this.layerIndex;
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModels.jaggi;
	}
}
