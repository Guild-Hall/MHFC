package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.index.ArmorMaterials;
import mhfc.net.common.index.ArmorModels;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.ItemRarity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class YukumoArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_yukumo_helm_name, ResourceInterface.armor_yukumo_chest_name,
			ResourceInterface.armor_yukumo_legs_name, ResourceInterface.armor_yukumo_boots_name };

	public YukumoArmor(EntityEquipmentSlot type) {
		super(ArmorMaterials.ArmorYukumo, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}
	
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
	
		if (stack.getItem() == MHFCItemRegistry.getRegistry().armor_yukumo_legs) {
			return  "mhfc:yukumo_layer_2.png";
		}
		return null;

	}
	

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModels.yukumo;
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
