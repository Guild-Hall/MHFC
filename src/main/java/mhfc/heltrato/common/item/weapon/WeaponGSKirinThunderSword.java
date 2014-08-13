package mhfc.heltrato.common.item.weapon;

import java.util.List;

import mhfc.heltrato.common.entity.mob.EntityTigrex;
import mhfc.heltrato.common.item.weapon.type.SemiLethalClass;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class WeaponGSKirinThunderSword extends SemiLethalClass {

	private float weaponDamage;

	public WeaponGSKirinThunderSword(ToolMaterial getType) {
		super(getType);
		setUnlocalizedName(MHFCReference.weapon_gs_kirin_name);
		weaponDamage = getType.getDamageVsEntity() - 4;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		par3List.add("\u00a79Thunder Element");
		par3List.add("\u00a72Semi Lethal Damage");

	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = par1IconRegister
				.registerIcon(MHFCReference.weapon_gs_kirin_icon);
	}

	public float getDamageVsEntity(Entity entity) {

		return weaponDamage;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase ent,
			EntityLivingBase player) {

		float damage = 0.0f;
		if (ent instanceof EntityTigrex) {
			damage = 57f;
		}

		DamageSource dmgSource = DamageSource
				.causePlayerDamage((EntityPlayer) player);
		ent.attackEntityFrom(dmgSource, damage);

		return true;
	}

}
