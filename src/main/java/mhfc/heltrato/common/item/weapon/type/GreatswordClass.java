package mhfc.heltrato.common.item.weapon.type;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import mhfc.heltrato.common.item.weapon.WeaponClass;
import mhfc.heltrato.common.item.weapon.WeaponMelee;
import mhfc.heltrato.common.item.weapon.WeaponMelee.WeaponSpecs;

public class GreatswordClass extends WeaponClass {

	public String gslocal = "greatsword_";
	
	public GreatswordClass(ToolMaterial getType) {
		super(new WeaponMelee(WeaponSpecs.GREATSWORD, getType));
		getWeaponDescription(clazz.greatswordname);
		getWeaponTextureloc(ref.weapon_gs_default_icon);
		getWeaponTable(-3, 3, 1);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target , EntityLivingBase player) {
		if(poisontype) target.addPotionEffect(new PotionEffect(Potion.poison.id, 30, amplified));
		if(firetype)  target.setFire(1);
		return true;
	}
	

}
