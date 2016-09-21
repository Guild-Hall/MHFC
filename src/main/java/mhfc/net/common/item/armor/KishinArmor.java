package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.reception.ArmorMaterialReception;
import mhfc.net.common.util.reception.ArmorModelReception;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KishinArmor extends ItemArmorMHFC {
	private static final String[] names = { Libraries.armor_tigrexb_helm_name,
			Libraries.armor_tigrexb_chest_name, Libraries.armor_tigrexb_legs_name,
			Libraries.armor_tigrexb_boots_name };

	public KishinArmor(EntityEquipmentSlot type) {
		super(ArmorMaterialReception.ArmorTigrexB, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModelReception.tigrexb;
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
