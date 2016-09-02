package mhfc.net.common.item.armor;

import mhfc.net.MHFCMain;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
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

	protected static final String[] baseIcons = { MHFCReference.base_gear_head, MHFCReference.base_gear_body,
			MHFCReference.base_gear_fauld, MHFCReference.base_gear_leg };

	protected ItemRarity rarity;

	public ArmorMHFC(ArmorMaterial armor, int renderIndex, EntityEquipmentSlot armorType) {
		super(armor, renderIndex, armorType);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public ArmorProperties getProperties(
			EntityLivingBase player,
			ItemStack armor,
			DamageSource source,
			double damage,
			int slot) {
		return null;
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return armorHeart;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {

	}
	
	public static void pleaseWork(ItemStack itemstack, ItemStack itemstack1, EntityPlayer clientPlayer, ModelBiped modelplayer){
		ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
		ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;
	if (itemstack != null)
	{
		modelbiped$armpose = ModelBiped.ArmPose.ITEM;

		if (clientPlayer.getItemInUseCount() > 0)
		{
			EnumAction enumaction = itemstack.getItemUseAction();

			if (enumaction == EnumAction.BLOCK)
			{
				modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
			}
			else if (enumaction == EnumAction.BOW)
			{
				modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
			}
		}
	}

	if (itemstack1 != null)
	{
		modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

		if (clientPlayer.getItemInUseCount() > 0)
		{
			EnumAction enumaction1 = itemstack1.getItemUseAction();

			if (enumaction1 == EnumAction.BLOCK)
			{
				modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
			}
		}
	}

	if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT)
	{
		modelplayer.rightArmPose = modelbiped$armpose;
		modelplayer.leftArmPose = modelbiped$armpose1;
	}
	else
	{
		modelplayer.rightArmPose = modelbiped$armpose1;
		modelplayer.leftArmPose = modelbiped$armpose;
	}
}

	public void displayInstancesStack(EntityLivingBase entity, int stack, int entID, DamageSource source) {
		//WIP for armors.. display in the description how much the armor can absords lethal damage from mobs..
		// SPECIFIC DAMAGE CALCULATIONS NOT JUST DIAMOND BASIS.
		// still thinking of how to implement.. /Heltrato
	}

}
