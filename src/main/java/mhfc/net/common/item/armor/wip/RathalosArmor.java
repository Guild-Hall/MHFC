package mhfc.net.common.item.armor.wip;

import java.util.List;

import javax.annotation.Nullable;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.index.armor.Material;
import mhfc.net.common.index.armor.Model;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.item.armor.ArmorBase;
import mhfc.net.common.util.Assert;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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
		case MAINHAND:
		case OFFHAND:
		default:
			Assert.logUnreachable("Armor can only be equiped on armor slots, got {}", armorSlot);
		}

		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		//	par3List.add("Attack Up L");
		//	par3List.add("Poison D[+4%]");
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
		return 40;
	}

	@Override
	protected int setFinalDefenseValue() {
		// TODO Auto-generated method stub
		return 150;
	}

	@Override
	protected void setAdditionalInformation(List<String> par) {
		// TODO Auto-generated method stub

	}

}
