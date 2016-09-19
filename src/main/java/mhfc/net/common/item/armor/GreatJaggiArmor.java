package mhfc.net.common.item.armor;

import mhfc.net.common.helper.ArmorMaterialHelper;
import mhfc.net.common.helper.ArmorModelHelper;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GreatJaggiArmor extends ItemArmorMHFC {
	private static final String[] names = { MHFCReference.armor_greatjaggi_helm_name,
			MHFCReference.armor_greatjaggi_chest_name, MHFCReference.armor_greatjaggi_legs_name,
			MHFCReference.armor_greatjaggi_boots_name };

	public GreatJaggiArmor(EntityEquipmentSlot type) {
		super(
				ArmorMaterialHelper.ArmorGreatJaggi,
				ItemRarity.R04,
				type,
				makeDefaultSlotToTex(MHFCReference.armor_greatjaggi_tex1, MHFCReference.armor_greatjaggi_tex2));
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModelHelper.jaggi;
	}
}
