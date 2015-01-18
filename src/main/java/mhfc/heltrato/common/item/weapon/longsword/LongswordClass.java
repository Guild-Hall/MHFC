package mhfc.heltrato.common.item.weapon.longsword;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import mhfc.heltrato.common.item.weapon.WeaponMelee;

public class LongswordClass extends WeaponMelee {
	
	public String lsLocal = "longsword_";
	
	
	public LongswordClass(ToolMaterial getType) {
		super(getType);
		getWeaponDescription(clazz.longswordname);
	    getWeaponTextureloc(ref.weapon_ls_default_icon);
	    getWeaponTable(4, -6, 0);
	    
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase ent, EntityLivingBase player)  {
	//	player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30, 1));
		
		return true;
	}


}
