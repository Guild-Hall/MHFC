package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.helper.ArmorMaterialHelper;
import mhfc.net.common.helper.ArmorModelHelper;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.system.DonatorSystem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class DragoonArmor extends ItemExclusiveArmor {
	private static final String[] names = { MHFCReference.armor_default_helm_name,
			MHFCReference.armor_default_chest_name, MHFCReference.armor_default_legs_name,
			MHFCReference.armor_default_boots_name };

	public DragoonArmor(EntityEquipmentSlot type) {
		super(
				DonatorSystem.dragoon,
				ArmorMaterialHelper.ArmorDragoon,
				ItemRarity.R04,
				type,
				makeDefaultSlotToTex(MHFCReference.armor_dragoon_tex1, MHFCReference.armor_dragoon_tex2),
				makeDefaultSlotToModel(ArmorModelHelper.dragoon));
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		par3List.add(
				ColorSystem.ENUMRED + "Exclusive " + "[ " + ColorSystem.ENUMGOLD + " DONATORS  " + ColorSystem.ENUMRED
						+ "]");
		par3List.add("Health +1");
	}

	@Override
	protected void applySetBonus(World world, EntityPlayer player) {
		float h = player.getHealth();
		player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 200, 1, true, true));
		player.setHealth(h);
	}
}
