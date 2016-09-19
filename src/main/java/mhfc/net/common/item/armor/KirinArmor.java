package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.helper.ArmorMaterialHelper;
import mhfc.net.common.helper.ArmorModelHelper;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class KirinArmor extends ItemArmorMHFC {
	private static final String[] names = { MHFCReference.armor_kirin_helm_name, MHFCReference.armor_kirin_chest_name,
			MHFCReference.armor_kirin_legs_name, MHFCReference.armor_kirin_boots_name };

	public KirinArmor(EntityEquipmentSlot type) {
		super(
				ArmorMaterialHelper.ArmorKirin,
				ItemRarity.R04,
				type,
				makeDefaultSlotToTex(MHFCReference.armor_kirin_tex1, MHFCReference.armor_kirin_tex2),
				makeDefaultSlotToModel(ArmorModelHelper.kirin));
		setUnlocalizedName(names[4 - type.getIndex()]);
	}

	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		par3List.add("Elemental Resistance L");
		par3List.add("Thunder + 15");
	}

}
