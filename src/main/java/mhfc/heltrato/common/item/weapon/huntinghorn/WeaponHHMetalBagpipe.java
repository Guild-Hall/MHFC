package mhfc.heltrato.common.item.weapon.huntinghorn;

import java.util.List;

import mhfc.heltrato.common.item.weapon.type.SiegeClass;
import mhfc.heltrato.common.util.Cooldown;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class WeaponHHMetalBagpipe extends HuntingHornClass {


	public WeaponHHMetalBagpipe(ToolMaterial getType) {
		super(getType, 2100);
		setUnlocalizedName(hhlocal + 1);
		getWeaponDescription("No Element",2);
		elementalType(false, false);
		enableCooldownDisplay = true;


	}


}
