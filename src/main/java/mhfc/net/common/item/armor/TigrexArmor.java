package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.index.ArmorMaterials;
import mhfc.net.common.index.ArmorModels;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.ItemRarity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TigrexArmor extends ItemArmorMHFC {
	private static final String[] names = { ResourceInterface.armor_tigrex_helm_name, ResourceInterface.armor_tigrex_chest_name,
			ResourceInterface.armor_tigrex_legs_name, ResourceInterface.armor_tigrex_boots_name };

	public TigrexArmor(EntityEquipmentSlot type) {
		super(ArmorMaterials.ArmorTigrex, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModels.tigrex;
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
}
