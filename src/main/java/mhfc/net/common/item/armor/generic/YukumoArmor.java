package mhfc.net.common.item.armor.generic;

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

import java.util.List;

public class YukumoArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_yukumo_helm_name,
			ResourceInterface.armor_yukumo_chest_name, ResourceInterface.armor_yukumo_legs_name,
			ResourceInterface.armor_yukumo_boots_name };

	public YukumoArmor(EntityEquipmentSlot type) {
		super(Material.yukomo, ItemRarity.R04, type);
		setTranslationKey(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		switch (armorSlot) {
		case HEAD:
			return Model.yukumo;
		case LEGS:
			return null;
		case FEET:
			return Model.yukumo;
		case CHEST:
			return Model.yukumo;
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
		return 0;
	}

	@Override
	protected String addHeadInfo() {
		return "Headgear from the Yukumo region. It identifies the wearer as a traveling hunter.";
	}

	@Override
	protected String addChestInfo() {
		return "Chest armor common among the people of the Yukumo region, it stresses mobility over defense.";
	}

	@Override
	protected String addLegsInfo() {
		return "Functional waist armor designed for convenience. Often wrapped around the shoulders for warmth.";
	}

	@Override
	protected String addBootsInfo() {
		return "Leg armor worn all over the Yukumo region. One can alter the hem width using the sash.";
	}

	@Override
	protected int setInitialDefenseValue() {
		return 10;
	}

	@Override
	protected int setFinalDefenseValue() {
		// TODO Auto-generated method stub
		return 270;
	}

	@Override
	protected void setAdditionalInformation(List<String> par) {
		par.add("+4 Thunder Resistance");
		par.add("-4 Fire Resistance");
	}

}
