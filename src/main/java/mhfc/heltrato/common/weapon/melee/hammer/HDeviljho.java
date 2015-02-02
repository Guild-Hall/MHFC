package mhfc.heltrato.common.weapon.melee.hammer;

import java.util.List;
import java.util.Random;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class HDeviljho extends HammerClass {

	public HDeviljho(ToolMaterial getType) {
		super(getType);
		getWeaponDescription("No Element",7);
		elementalType(false, false);
		setUnlocalizedName(hmlocal + 6);
	}
}
