package mhfc.net.common.item.armor.generic;

import java.util.List;

import mhfc.net.common.entity.monster.wip.EntityKirin;
import mhfc.net.common.entity.monster.wip.EntityLagiacrus;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.index.armor.Material;
import mhfc.net.common.index.armor.Model;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.item.armor.ArmorBase;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.util.Assert;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BarrothArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_barroth_helm_name,
			ResourceInterface.armor_barroth_chest_name, ResourceInterface.armor_barroth_legs_name,
			ResourceInterface.armor_barroth_boots_name };

	public BarrothArmor(EntityEquipmentSlot type) {
		super(Material.barroth, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
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
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		par3List.add(ColorSystem.ENUMLAVENDER + "Initial Defense: " + ColorSystem.ENUMWHITE + "75");
		par3List.add(ColorSystem.ENUMLAVENDER + "Maximum Defense: " + ColorSystem.ENUMWHITE + "150");
		par3List.add("+15 Thunder Resistance");
		par3List.add("-15 Ice Resistance");
		switch (this.armorType) {
		case HEAD:
			par3List.add(
					ColorSystem.ENUMAQUA
							+ "Headgear made from Barroth armor. Won't crack, even if trampled on by a wyvern.");
			break;
		case CHEST:
			par3List.add(
					ColorSystem.ENUMAQUA
							+ "Chest armor made for pure rigidity. Forged to be harder than anything else.");
			break;
		case LEGS:
			par3List.add(
					ColorSystem.ENUMAQUA + "Waist armor made from extraordinarily hard, fortress-like Barroth armor.");
			break;
		case FEET:
			par3List.add(
					ColorSystem.ENUMAQUA
							+ "Leg armor made from Barroth feet. They act as a tough outer skeleton for the legs.");
			break;
		case MAINHAND:
		case OFFHAND:
		default:
			Assert.logUnreachable("Armor can only be equiped on armor slots, got ", this.armorType);
		}
	}

	@Override
	public ArmorProperties getProperties(
			EntityLivingBase player,
			ItemStack armor,
			DamageSource source,
			double damage,
			int slot) {

		/**
		 * The initial absorption for monsters that are not included in the armors element excemption.
		 **/
		if (source.getSourceOfDamage() instanceof EntityMHFCBase) {
			/** Ratio base is 10 ( which is initial defense, 270 is maximum defense. **/
			return new ArmorProperties(1, 0.20, 150);
		} else if (source.getSourceOfDamage() instanceof EntityKirin
				&& source.getSourceOfDamage() instanceof EntityLagiacrus) {
			return new ArmorProperties(1, 0.20 + 0.10, 150);
		}
		// TO BE ADDED WEAKNESS IN ICE ELEMENTS  LEL

		/*else if (source.getSourceOfDamage() instanceof EntityRathalos) {
			return new ArmorProperties(1, ((0.75 - .01) / 100), 270);
		}*/
		return new ArmorProperties(1, 100, 270);

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



}
