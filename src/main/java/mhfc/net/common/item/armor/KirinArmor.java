package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.reception.ArmorMaterialReception;
import mhfc.net.common.util.reception.ArmorModelReception;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KirinArmor extends ItemArmorMHFC {
	private static final String[] names = { Libraries.armor_kirin_helm_name, Libraries.armor_kirin_chest_name,
			Libraries.armor_kirin_legs_name, Libraries.armor_kirin_boots_name };

	public KirinArmor(EntityEquipmentSlot type) {
		super(ArmorMaterialReception.ArmorKirin, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModelReception.kirin;
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
