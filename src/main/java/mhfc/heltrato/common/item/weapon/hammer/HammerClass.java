package mhfc.heltrato.common.item.weapon.hammer;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import mhfc.heltrato.common.item.weapon.WeaponClass;

public class HammerClass extends WeaponClass {

	public String hmlocal = "hammer_";
	
	public HammerClass(ToolMaterial getType) {
		super(getType);
		
		getWeaponDescription(clazz.hammername);
		getWeaponTextureloc(ref.weapon_hm_default_icon);
		getWeaponTable(-5, 7, 1);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target , EntityLivingBase player) {
		if(rand.nextInt() == 60)target.motionX -= knockback; target.motionY += 0.5D;
		if(poisontype) target.addPotionEffect(new PotionEffect(Potion.poison.id, 30, amplified));
		if(firetype)  target.setFire(1);
		return true;
	}
	

}
