package mhfc.net.common.item.armor.wip;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.index.armor.Material;
import mhfc.net.common.index.armor.Model;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.item.armor.ArmorBase;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.util.Assert;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class KirinSArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_kirinS_helm_name,
			ResourceInterface.armor_kirinS_chest_name, ResourceInterface.armor_kirinS_legs_name,
			ResourceInterface.armor_kirinS_boots_name };

	public KirinSArmor(EntityEquipmentSlot type) {
		super(Material.kirinS, ItemRarity.R04, type);
		setTranslationKey(names[3 - type.getIndex()]);
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
		case MAINHAND:
		case OFFHAND:
		default:
			Assert.logUnreachable("Armor can only be equiped on armor slots, got {}", armorSlot);
		}

		return null;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ColorSystem.ENUMAQUA + "[ Donators Exclusive ");
		switch (this.armorType) {
		case HEAD:
			tooltip.add("\u00a79Kirin S Class Helmet");
			break;
		case CHEST:
			tooltip.add("\u00a79Kirin S Class Chest");
			break;
		case LEGS:
			tooltip.add("\u00a79Kirin S Class Leggings");
			break;
		case FEET:
			tooltip.add("\u00a79Kirin S Class Boots");
			break;
		case MAINHAND:
		case OFFHAND:
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

	@Override
	public ArmorProperties getProperties(
			EntityLivingBase player,
			ItemStack armor,
			DamageSource source,
			double damage,
			int slot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	protected String addHeadInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String addChestInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String addLegsInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String addBootsInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int setInitialDefenseValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int setFinalDefenseValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void setAdditionalInformation(List<String> par) {
		// TODO Auto-generated method stub

	}

}
