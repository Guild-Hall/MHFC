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

public class BarrothArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_barroth_helm_name,
			ResourceInterface.armor_barroth_chest_name, ResourceInterface.armor_barroth_legs_name,
			ResourceInterface.armor_barroth_boots_name };

	public BarrothArmor(EntityEquipmentSlot type) {
		super(Material.barroth, ItemRarity.R04, type);
		setTranslationKey(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		switch (armorSlot) {
		case HEAD:
			return Model.barroth;
		case LEGS:
			return null;
		case FEET:
			return Model.barroth;
		case CHEST:
			return Model.barroth;
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
			return 0;
		} else if (slot == 1) {
			return 0;
		} else if (slot == 2) {
			return 1;
		}
		return 1;
	}


	@Override
	protected String addHeadInfo() {
		return "Headgear made from Barroth armor. Won't crack, even if trampled on by a wyvern.";
	}

	@Override
	protected String addChestInfo() {
		return "Chest armor made for pure rigidity. Forged to be harder than anything else.";
	}

	@Override
	protected String addLegsInfo() {
		return "Waist armor made from extraordinarily hard, fortress-like Barroth armor.";
	}

	@Override
	protected String addBootsInfo() {
		return "Leg armor made from Barroth feet. They act as a tough outer skeleton for the legs.";
	}

	@Override
	protected int setInitialDefenseValue() {
		return 75;
	}

	@Override
	protected int setFinalDefenseValue() {
		return 150;
	}

	@Override
	protected void setAdditionalInformation(List<String> par) {
		par.add("+15 Thunder Resistance");
		par.add("-15 Ice Resistance");
	}

}
