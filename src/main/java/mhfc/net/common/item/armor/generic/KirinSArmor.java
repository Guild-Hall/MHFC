package mhfc.net.common.item.armor.generic;

import java.util.List;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.index.armor.Material;
import mhfc.net.common.index.armor.Model;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.item.armor.ArmorBase;
import mhfc.net.common.system.ColorSystem;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KirinSArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_kirinS_helm_name,
			ResourceInterface.armor_kirinS_chest_name, ResourceInterface.armor_kirinS_legs_name,
			ResourceInterface.armor_kirinS_boots_name };

	public KirinSArmor(EntityEquipmentSlot type) {
		super(Material.kirinS, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		switch (armorSlot) {
		case HEAD:
			return Model.kirinS;
		case LEGS:
			return null;
		case FEET:
			return Model.kirinS;
		case CHEST:
			return Model.kirinS;
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
		par3List.add( ColorSystem.ENUMAQUA + "[ Donators Exclusive ");
		switch (this.armorType) {
		case HEAD:
			par3List.add("\u00a79Kirin S Class Helmet");
			break;
		case CHEST:
			par3List.add("\u00a79Kirin S Class Chest");
			break;
		case LEGS:
			par3List.add("\u00a79Kirin S Class Leggings");
			break;
		case FEET:
			par3List.add("\u00a79Kirin S Class Boots");
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void applySetBonus(World world, EntityPlayer player) {
		if (player.getHealth() <= 3F) {
			player.heal(2F);
		}
		world.spawnParticle(
				EnumParticleTypes.CLOUD,
				player.posX + Item.itemRand.nextFloat() * 2.0F - 1.0D,
				player.posY + Item.itemRand.nextFloat() * 3.0F + 1.0D,
				player.posZ + Item.itemRand.nextFloat() * 2.0F - 1.0D,
				0.0D,
				0.0D,
				0.0D);
	}
}
