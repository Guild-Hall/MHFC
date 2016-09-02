package mhfc.net.common.item.armor;

import mhfc.net.common.helper.ArmorMaterialHelper;
import mhfc.net.common.helper.ArmorModelHelper;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.inventory.EntityEquipmentSlot;

public class GreatJaggiArmor extends ItemArmorMHFC {
	private static final String[] names = { MHFCReference.armor_greatjaggi_helm_name,
			MHFCReference.armor_greatjaggi_chest_name, MHFCReference.armor_greatjaggi_legs_name,
			MHFCReference.armor_greatjaggi_boots_name };

	public GreatJaggiArmor(EntityEquipmentSlot type) {
		super(
				ArmorMaterialHelper.ArmorGreatJaggi,
				ItemRarity.R04,
				type,
				makeDefaultSlotToTex(MHFCReference.armor_greatjaggi_tex1, MHFCReference.armor_greatjaggi_tex2),
				makeDefaultSlotToModel(ArmorModelHelper.jaggi));
		setUnlocalizedName(names[type.getIndex()]);
	}
}
