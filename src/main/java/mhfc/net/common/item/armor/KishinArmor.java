package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.helper.ArmorMaterialHelper;
import mhfc.net.common.helper.ArmorModelHelper;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class KishinArmor extends ItemArmorMHFC {
	private static final String[] names = { MHFCReference.armor_tigrexb_helm_name,
			MHFCReference.armor_tigrexb_chest_name, MHFCReference.armor_tigrexb_legs_name,
			MHFCReference.armor_tigrexb_boots_name };

	public KishinArmor(EntityEquipmentSlot type) {
		super(
				ArmorMaterialHelper.ArmorTigrexB,
				ItemRarity.R04,
				type,
				makeDefaultSlotToTex(MHFCReference.armor_tigrexb_tex1, MHFCReference.armor_tigrexb_tex2),
				makeDefaultSlotToModel(ArmorModelHelper.tigrexb));
		setUnlocalizedName(names[type.getIndex()]);
	}

	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		par3List.add("Attack Up (S)");
		switch (this.armorType) {
		case HEAD:
			par3List.add(ColorSystem.dark_blue + "Tigrex X Class Helmet");
			break;
		case CHEST:
			par3List.add(ColorSystem.dark_blue + "Tigrex X Class Chest");
			break;
		case LEGS:
			par3List.add(ColorSystem.dark_blue + "Tigrex X Class Leggings");
			break;
		case FEET:
			par3List.add(ColorSystem.dark_blue + "Tigrex X Class Boots");
			break;
		default:
			break;
		}
	}

}
