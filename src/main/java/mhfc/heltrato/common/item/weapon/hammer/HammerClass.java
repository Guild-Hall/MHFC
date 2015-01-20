package mhfc.heltrato.common.item.weapon.hammer;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import mhfc.heltrato.common.item.weapon.WeaponClass;
import mhfc.heltrato.common.item.weapon.WeaponMelee;
import mhfc.heltrato.common.item.weapon.WeaponMelee.WeaponSpecs;

public class HammerClass extends WeaponClass {

	public String hmlocal = "hammer_";
	
	public HammerClass(ToolMaterial getType) {
		super(new WeaponMelee(WeaponSpecs.HAMMER, getType));
		
		getWeaponDescription(clazz.hammername);
		getWeaponTextureloc(ref.weapon_hm_default_icon);
		getWeaponTable(-5, 7, 1);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target , EntityLivingBase player) {
		if(poisontype) target.addPotionEffect(new PotionEffect(Potion.poison.id, 30, amplified));
		if(firetype)  target.setFire(1);
		return true;
	}
	

}
