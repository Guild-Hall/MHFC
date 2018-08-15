package mhfc.net.common.item.armor.wip;

import java.util.List;

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

public class KishinArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_tigrexb_helm_name,
			ResourceInterface.armor_tigrexb_chest_name, ResourceInterface.armor_tigrexb_legs_name,
			ResourceInterface.armor_tigrexb_boots_name };

	public KishinArmor(EntityEquipmentSlot type) {
		super(Material.kishin, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		switch (armorSlot) {
		case HEAD:
			return Model.kishin;
		case LEGS:
			return null;
		case FEET:
			return Model.kishin;
		case CHEST:
			return Model.kishin;
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
		par3List.add("Attack Up (S)");
		switch (this.armorType) {
		case HEAD:
			par3List.add(ColorSystem.dark_blue + "Tigrex X Class Helmet");
			break;
		case CHEST:
			par3List.add(ColorSystem.dark_blue + "Tigrex X Class Chest");
			break;
		case LEGS:
			par3List.add(ColorSystem.dark_blue + "Tigrex X Class Leggings");
			break;
		case FEET:
			par3List.add(ColorSystem.dark_blue + "Tigrex X Class Boots");
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
