package mhfc.net.common.item.armor.community;

import java.util.List;

import mhfc.net.common.helper.ArmorMaterialHelper;
import mhfc.net.common.helper.ArmorModelHelper;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.item.armor.ItemExclusiveArmor;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.system.DonatorSystem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 *
 * @author WorldSEnder, design by Sean Tang
 *
 */
public class ST_BionicArmor extends ItemExclusiveArmor {
	private static final String[] names = { MHFCReference.armor_bionic_helm_name, MHFCReference.armor_bionic_chest_name,
			MHFCReference.armor_bionic_legs_name, MHFCReference.armor_bionic_boots_name };

	public ST_BionicArmor(EntityEquipmentSlot type) {
		super(
				DonatorSystem.bionic,
				ArmorMaterialHelper.ArmorST_1_Bionic,
				ItemRarity.R04,
				type,
				makeDefaultSlotToTex(MHFCReference.armor_bionic_tex1, MHFCReference.armor_bionic_tex2),
				makeDefaultSlotToModel(ArmorModelHelper.bionic));
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		par3List.add(
				ColorSystem.ENUMRED + "(DONATORS EXCLUSIVE) Designed from Sean Tang" + "[ " + ColorSystem.ENUMGOLD
						+ " ST - 1 'Bionic'   " + ColorSystem.ENUMRED + "]");
	}

}
