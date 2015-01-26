package mhfc.heltrato.common.item.weapon.greatsword;

import java.util.List;

import mhfc.heltrato.common.item.weapon.type.GreatswordClass;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class WeaponGSRathalosFiresword extends GreatswordClass {

	private float weaponDamage;

	public WeaponGSRathalosFiresword(ToolMaterial getType) {
		super(getType);
		getWeaponDescription("Fire Element", 3);
		elementalType(false, true);
		setUnlocalizedName(gslocal + 5);	}


}
