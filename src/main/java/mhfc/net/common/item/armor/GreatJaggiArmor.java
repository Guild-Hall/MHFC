package mhfc.net.common.item.armor;

import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.reception.ArmorMaterialReception;
import mhfc.net.common.util.reception.ArmorModelReception;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GreatJaggiArmor extends ItemArmorMHFC {
	private static final String[] names = { Libraries.armor_greatjaggi_helm_name,
			Libraries.armor_greatjaggi_chest_name, Libraries.armor_greatjaggi_legs_name,
			Libraries.armor_greatjaggi_boots_name };

	public GreatJaggiArmor(EntityEquipmentSlot type) {
		super(ArmorMaterialReception.ArmorGreatJaggi, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModelReception.jaggi;
	}
}
