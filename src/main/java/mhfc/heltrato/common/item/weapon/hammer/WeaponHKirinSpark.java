package mhfc.heltrato.common.item.weapon.hammer;

import java.util.List;
import java.util.Random;

import mhfc.heltrato.common.item.weapon.type.SiegeClass;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class WeaponHKirinSpark extends HammerClass {


	public WeaponHKirinSpark(ToolMaterial getType) {
		super(getType);
		getWeaponDescription("Thunder Element",7);
		elementalType(false, false);
		setUnlocalizedName(hmlocal + 2);
	}


}
