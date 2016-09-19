package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.helper.ArmorMaterialHelper;
import mhfc.net.common.helper.ArmorModelHelper;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class RathalosArmor extends ItemArmorMHFC {
	private static final String[] names = { MHFCReference.armor_rathalos_helm_name,
			MHFCReference.armor_rathalos_chest_name, MHFCReference.armor_rathalos_legs_name,
			MHFCReference.armor_rathalos_boots_name };

	public RathalosArmor(EntityEquipmentSlot type) {
		super(
				ArmorMaterialHelper.ArmorRathalos,
				ItemRarity.R04,
				type,
				makeDefaultSlotToTex(MHFCReference.armor_rathalos_tex1, MHFCReference.armor_rathalos_tex2),
				makeDefaultSlotToModel(ArmorModelHelper.rathalos));
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		par3List.add("Attack Up L");
		par3List.add("Poison D[+4%]");
		par3List.add("+ 10 Fire");
		par3List.add("+ 15 Thunder");
	}

}
