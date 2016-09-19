package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.helper.ArmorMaterialHelper;
import mhfc.net.common.helper.ArmorModelHelper;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class YukumoArmor extends ItemArmorMHFC {
	private static final String[] names = { MHFCReference.armor_yukumo_helm_name, MHFCReference.armor_yukumo_chest_name,
			MHFCReference.armor_yukumo_legs_name, MHFCReference.armor_yukumo_boots_name };

	public YukumoArmor(EntityEquipmentSlot type) {
		super(
				ArmorMaterialHelper.ArmorYukumo,
				ItemRarity.R04,
				type,
				makeDefaultSlotToTex(MHFCReference.armor_yukumo_tex1, MHFCReference.armor_yukumo_tex2),
				makeDefaultSlotToModel(ArmorModelHelper.yukumo));
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		par3List.add("+5 Thunder");
	}
}
