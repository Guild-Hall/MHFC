package mhfc.net.common.item.armor;

import mhfc.net.MHFCMain;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.util.lib.MHFCReference;
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

	protected static final String[] baseIcons = { MHFCReference.base_gear_head, MHFCReference.base_gear_body,
			MHFCReference.base_gear_fauld, MHFCReference.base_gear_leg };

	protected ItemRarity rarity;

	public ArmorMHFC(ArmorMaterial armor, int renderIndex, int armorType) {
		super(armor, renderIndex, armorType);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player,ItemStack armor,DamageSource source,double damage, int slot) {
		double armourReduction = 0.0;
		double damageAmount = 0.25;
		
		if(slot == 0){
			damageAmount = 3d / 20d * 0.6;
		}else if(slot == 1){
			damageAmount = 0.8;
		}else if(slot == 2){
			damageAmount = 6d / 20d * 0.6;
		}else if(slot == 3){
			damageAmount = 6d / 20d * 0.6;
		}
		 if (source.equals(DamageSource.causeMobDamage(player.getLastAttacker())))
	        {	
			 if(player.getLastAttacker() instanceof EntityMHFCBase){
				 damageAmount = 2D/20 * 0.3;
			 }
			 
	        }
		
		return null;
	}
	
	public int chestArmordisplay;
	public int LegArmordisplay;
	public int headArmordisplay;

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if(slot == 1){
			return chestArmordisplay;
		}else if (slot == 2) {
			return LegArmordisplay;
		}
		return headArmordisplay;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {

	}
	
	public void addArmorEffectAttribute(){
		
	}


}
