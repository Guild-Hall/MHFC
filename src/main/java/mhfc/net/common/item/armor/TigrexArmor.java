package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.reception.ArmorMaterialReception;
import mhfc.net.common.util.reception.ArmorModelReception;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TigrexArmor extends ItemArmorMHFC {
	private static final String[] names = { Libraries.armor_tigrex_helm_name, Libraries.armor_tigrex_chest_name,
			Libraries.armor_tigrex_legs_name, Libraries.armor_tigrex_boots_name };

	public TigrexArmor(EntityEquipmentSlot type) {
		super(ArmorMaterialReception.ArmorTigrex, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModelReception.tigrex;
	}

	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		par3List.add("Quick Eating L");
		par3List.add("+ 15 Fire");
		par3List.add("- 10 Thunder");
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
