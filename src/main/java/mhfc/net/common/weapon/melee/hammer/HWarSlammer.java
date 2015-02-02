package mhfc.net.common.weapon.melee.hammer;

import java.util.List;
import java.util.Random;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class HWarSlammer extends HammerClass {


	public HWarSlammer(ToolMaterial getType) {
		super(getType);
		getWeaponDescription("No",2);
		elementalType(false, false);
		setUnlocalizedName(hmlocal + 5);
	}
}
