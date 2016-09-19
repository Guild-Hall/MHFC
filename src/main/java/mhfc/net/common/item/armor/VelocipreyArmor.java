package mhfc.net.common.item.armor;

import mhfc.net.common.helper.ArmorMaterialHelper;
import mhfc.net.common.helper.ArmorModelHelper;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.inventory.EntityEquipmentSlot;

public class VelocipreyArmor extends ItemArmorMHFC {
	private static final String[] names = { MHFCReference.armor_velociprey_helm_name,
			MHFCReference.armor_velociprey_chest_name, MHFCReference.armor_velociprey_legs_name,
			MHFCReference.armor_velociprey_boots_name };

	public VelocipreyArmor(EntityEquipmentSlot type) {
		super(
				ArmorMaterialHelper.ArmorVelociprey,
				ItemRarity.R04,
				type,
				makeDefaultSlotToTex(MHFCReference.armor_velociprey_tex1, MHFCReference.armor_velociprey_tex2),
				makeDefaultSlotToModel(ArmorModelHelper.velociprey));
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

}
