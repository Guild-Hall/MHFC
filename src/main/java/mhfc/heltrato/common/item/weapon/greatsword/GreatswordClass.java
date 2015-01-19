package mhfc.heltrato.common.item.weapon.greatsword;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import mhfc.heltrato.common.item.weapon.WeaponMelee;

public class GreatswordClass extends WeaponMelee {

	public String gslocal = "greatsword_";
	
	public GreatswordClass(ToolMaterial getType) {
		super(getType);
		getWeaponDescription(clazz.greatswordname);
		getWeaponTextureloc(ref.weapon_gs_default_icon);
		getWeaponTable(-3, 3, 1);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target , EntityLivingBase player) {
		if(rand.nextInt() == 20)target.motionX -= knockback;
		if(poisontype) target.addPotionEffect(new PotionEffect(Potion.poison.id, 30, amplified));
		if(firetype)  target.setFire(1);
		return true;
	}
	

}
