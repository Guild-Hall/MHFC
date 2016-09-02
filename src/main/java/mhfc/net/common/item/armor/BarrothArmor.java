package mhfc.net.common.item.armor;

import mhfc.net.common.helper.ArmorMaterialHelper;
import mhfc.net.common.helper.ArmorModelHelper;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.inventory.EntityEquipmentSlot;

public class BarrothArmor extends ItemArmorMHFC {
	private static final String[] names = { MHFCReference.armor_barroth_helm_name,
			MHFCReference.armor_barroth_chest_name, MHFCReference.armor_barroth_legs_name,
			MHFCReference.armor_barroth_boots_name };

	public BarrothArmor(EntityEquipmentSlot type) {
		super(
				ArmorMaterialHelper.ArmorBarroth,
				ItemRarity.R04,
				type,
				makeDefaultSlotToTex(MHFCReference.armor_barroth_tex1, MHFCReference.armor_barroth_tex2),
				makeDefaultSlotToModel(ArmorModelHelper.barroth));
		setUnlocalizedName(names[type.getIndex()]);
	}
}
