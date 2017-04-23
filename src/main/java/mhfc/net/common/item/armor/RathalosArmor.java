package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.index.armor.Material;
import mhfc.net.common.index.armor.Model;
import mhfc.net.common.item.ItemRarity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RathalosArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_rathalos_helm_name,
			ResourceInterface.armor_rathalos_chest_name, ResourceInterface.armor_rathalos_legs_name,
			ResourceInterface.armor_rathalos_boots_name };

	public RathalosArmor(EntityEquipmentSlot type) {
		super(Material.rathalos, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		switch (armorSlot) {
		case HEAD:
			return Model.rathalos;
		case LEGS:
			return null;
		case FEET:
			return Model.rathalos;
		case CHEST:
			return Model.rathalos;
		default:
			break;

		}

		return null;
	}

	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		//	par3List.add("Attack Up L");
		//	par3List.add("Poison D[+4%]");
	}

}
