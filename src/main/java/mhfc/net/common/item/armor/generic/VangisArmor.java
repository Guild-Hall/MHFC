package mhfc.net.common.item.armor.generic;

import java.util.List;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.index.armor.Material;
import mhfc.net.common.index.armor.Model;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.item.armor.ArmorBase;
import mhfc.net.common.util.Assert;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VangisArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_deviljho_helm_name,
			ResourceInterface.armor_deviljho_chest_name, ResourceInterface.armor_deviljho_legs_name,
			ResourceInterface.armor_deviljho_boots_name };

	public VangisArmor(EntityEquipmentSlot type) {
		super(Material.deviljho, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		switch (armorSlot) {
		case HEAD:
			return Model.deviljho;
		case LEGS:
			return null;
		case FEET:
			return Model.deviljho;
		case CHEST:
			return Model.deviljho;
		case MAINHAND:
		case OFFHAND:
		default:
			Assert.logUnreachable("Armor can only be equiped on armor slots, got {}", armorSlot);
		}

		return null;
	}



	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (slot == 0) {
			return 2;
		} else if (slot == 1) {
			return 2;
		} else if (slot == 2) {
			return 2;
		}
		return 1;
	}

	@Override
	protected String addHeadInfo() {
		return "Head armor made from stiff, tanned Deviljho hide. Inspires destructive impulses.";
	}

	@Override
	protected String addChestInfo() {
		return "Jet-black armor steeped in Deviljho rage. Brings power, but also rapid hunger.";
	}

	@Override
	protected String addLegsInfo() {
		return "Waist armor affixed with a giant emblem. Turns vision red with unchecked rage.";
	}

	@Override
	protected String addBootsInfo() {
		return "Greaves instilled with the Deviljho's leg power. Each step leaves craters.";
	}

	@Override
	protected int setInitialDefenseValue() {
		// TODO Auto-generated method stub
		return 310;
	}

	@Override
	protected int setFinalDefenseValue() {
		// TODO Auto-generated method stub
		return 565;
	}

	@Override
	protected void setAdditionalInformation(List<String> par) {
		par.add("+5 Ice Resistance");
		par.add("-20 Thunder Resistance");
		par.add("-20 Dragon Resistance");

	}

}
