package mhfc.heltrato.common.item.weapon.greatsword;

import java.util.List;

import mhfc.heltrato.common.item.weapon.type.SemiLethalClass;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class WeaponGSBone extends GreatswordClass {

	private float weaponDamage;

	public WeaponGSBone(ToolMaterial getType) {
		super(getType);
		getWeaponDescription("No Element", 1);
		elementalType(false, false);
		setUnlocalizedName(gslocal + 1);
	}


}
