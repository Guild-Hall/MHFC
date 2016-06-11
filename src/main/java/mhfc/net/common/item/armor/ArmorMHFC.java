package mhfc.net.common.item.armor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.common.helper.MHFCArmorModelHelper;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

public class ArmorMHFC extends ItemArmor implements ISpecialArmor {
	/**
	 * TODO
	 *
	 * Soon this will be the basing of all mhfc armor
	 *
	 */
	public static int modelID;
	public static int armorHeart;

	protected static final String[] baseIcons = {
		MHFCReference.base_gear_head,
		MHFCReference.base_gear_body,
		MHFCReference.base_gear_fauld,
		MHFCReference.base_gear_leg
	};

	protected ItemRarity rarity;

	public ArmorMHFC(ArmorMaterial armor, int renderIndex, int armorType) {
		super(armor, renderIndex, armorType);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving,
			ItemStack itemStack, int armorSlot) {

		if (itemStack == null)
			return null;
		ModelBiped armorModel = MHFCArmorModelHelper.getArmorModel(modelID);

		if (armorModel != null) {
			armorModel.bipedHead.showModel = armorSlot == 0;
			armorModel.bipedHeadwear.showModel = armorSlot == 0;
			armorModel.bipedBody.showModel = armorSlot == 1 || armorSlot == 2;
			armorModel.bipedRightArm.showModel = armorSlot == 1;
			armorModel.bipedLeftArm.showModel = armorSlot == 1;
			armorModel.bipedRightLeg.showModel = armorSlot == 2
					|| armorSlot == 3;
			armorModel.bipedLeftLeg.showModel = armorSlot == 2
					|| armorSlot == 3;

			armorModel.isSneak = entityLiving.isSneaking();
			armorModel.isRiding = entityLiving.isRiding();
			armorModel.isChild = entityLiving.isChild();
			armorModel.heldItemRight = entityLiving.getEquipmentInSlot(0) != null
					? 1
					: 0;
			if (entityLiving instanceof EntityPlayer) {

				armorModel.aimedBow = ((EntityPlayer) entityLiving)
						.getItemInUseDuration() > 2;
			}
			return armorModel;
		}
		return null;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player,
			ItemStack armor, DamageSource source, double damage, int slot) {
		return null;
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return armorHeart;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) {

	}

	public void displayInstancesStack(EntityLivingBase entity, int stack, int entID, DamageSource source)
	{
		//WIP for armors.. display in the description how much the armor can absords lethal damage from mobs..
		// SPECIFIC DAMAGE CALCULATIONS NOT JUST DIAMOND BASIS.
		// still thinking of how to implement.. /Heltrato
	}

}
