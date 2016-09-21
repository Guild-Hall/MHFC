package mhfc.net.common.item.armor.community;

import java.util.List;

import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.item.armor.ItemExclusiveArmor;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.system.DonatorSystem;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.reception.ArmorMaterialReception;
import mhfc.net.common.util.reception.ArmorModelReception;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author WorldSEnder, design by Sean Tang
 *
 */
public class ST_BionicArmor extends ItemExclusiveArmor {
	private static final String[] names = { Libraries.armor_bionic_helm_name, Libraries.armor_bionic_chest_name,
			Libraries.armor_bionic_legs_name, Libraries.armor_bionic_boots_name };

	public ST_BionicArmor(EntityEquipmentSlot type) {
		super(DonatorSystem.bionic, ArmorMaterialReception.ArmorST_1_Bionic, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModelReception.bionic;
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
