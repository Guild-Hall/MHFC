package mhfc.net.common.item.armor;

import java.util.List;

import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.system.DonatorSystem;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.reception.ArmorMaterialReception;
import mhfc.net.common.util.reception.ArmorModelReception;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DragoonArmor extends ItemExclusiveArmor {
	private static final String[] names = { Libraries.armor_dragoon_helm_name,
			Libraries.armor_dragoon_chest_name, Libraries.armor_dragoon_legs_name,
			Libraries.armor_dragoon_boots_name };

	public DragoonArmor(EntityEquipmentSlot type) {
		super(DonatorSystem.dragoon, ArmorMaterialReception.ArmorDragoon, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		return ArmorModelReception.dragoon;
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
