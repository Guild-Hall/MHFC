package mhfc.net.common.item.armor;

import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.reception.ArmorMaterialReception;
import mhfc.net.common.util.reception.ArmorModelReception;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BarrothArmor extends ItemArmorMHFC {
	private static final String[] names = { Libraries.armor_barroth_helm_name,
			Libraries.armor_barroth_chest_name, Libraries.armor_barroth_legs_name,
			Libraries.armor_barroth_boots_name };

	public BarrothArmor(EntityEquipmentSlot type) {
		super(ArmorMaterialReception.ArmorBarroth, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModelReception.barroth;
	}
}
