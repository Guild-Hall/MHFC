package mhfc.net.common.item.armor.generic;

import java.util.List;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.index.armor.Material;
import mhfc.net.common.index.armor.Model;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.item.armor.ArmorBase;
import mhfc.net.common.util.Assert;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TigrexArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_tigrex_helm_name,
			ResourceInterface.armor_tigrex_chest_name, ResourceInterface.armor_tigrex_legs_name,
			ResourceInterface.armor_tigrex_boots_name };

	public TigrexArmor(EntityEquipmentSlot type) {
		super(Material.tigrex, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		switch (armorSlot) {
		case HEAD:
			return Model.tigrex;
		case LEGS:
			return null;
		case FEET:
			return Model.tigrex;
		case CHEST:
			return Model.tigrex;
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
		//	par3List.add("Quick Eating L");
	}

	@Override
	protected void applySetBonus(World world, EntityPlayer player) {
		ItemStack equipement = player.getActiveItemStack();
		if (equipement != null && equipement.getItem() instanceof ItemFood) {
			// int maxUseDuration = equipement.getItem().getMaxItemUseDuration(equipement);
			// int currentDuration = player.getItemInUseCount();
			// TODO: whatever was planned for tigrex armor
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

}
