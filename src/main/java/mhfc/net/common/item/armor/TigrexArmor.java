package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.helper.ArmorMaterialHelper;
import mhfc.net.common.helper.ArmorModelHelper;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TigrexArmor extends ItemArmorMHFC {
	private static final String[] names = { MHFCReference.armor_tigrex_helm_name, MHFCReference.armor_tigrex_chest_name,
			MHFCReference.armor_tigrex_legs_name, MHFCReference.armor_tigrex_boots_name };

	public TigrexArmor(EntityEquipmentSlot type) {
		super(
				ArmorMaterialHelper.ArmorTigrex,
				ItemRarity.R04,
				type,
				makeDefaultSlotToTex(MHFCReference.armor_tigrex_tex1, MHFCReference.armor_tigrex_tex2),
				makeDefaultSlotToModel(ArmorModelHelper.tigrex));
		setUnlocalizedName(names[type.getIndex()]);
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
