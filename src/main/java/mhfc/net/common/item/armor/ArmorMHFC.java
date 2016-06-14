package mhfc.net.common.item.armor;

import mhfc.net.MHFCMain;
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

	public ArmorMHFC(ArmorMaterial armor, int renderIndex, int armorType) {
		super(armor, renderIndex, armorType);
		setCreativeTab(MHFCMain.mhfctabs);
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
