package mhfc.heltrato.common.item.weapon.greatsword;

import mhfc.heltrato.common.item.weapon.WeaponMelee;

public class GreatswordClass extends WeaponMelee {

	public String gslocal = "greatsword_";
	int amplified;
	
	public GreatswordClass(ToolMaterial getType) {
		super(getType);
		getWeaponDescription(clazz.greatswordname);
		getWeaponTextureloc(ref.weapon_gs_default_icon);
		getWeaponTable(-3, 3, 2);
	}
	

}
