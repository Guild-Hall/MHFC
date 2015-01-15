package mhfc.heltrato.common.item.weapon.hammer;

import java.util.List;
import java.util.Random;

import mhfc.heltrato.MHFCMain;
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

public class WeaponHRathalos extends SiegeClass {

	private Random rand;
	private float weaponDamage;

	public WeaponHRathalos(ToolMaterial getType) {
		super(getType);
		setUnlocalizedName(MHFCReference.weapon_hm_rathalos_name);
		setCreativeTab(MHFCMain.mhfctabs);
		setFull3D();
		rand = new Random();
		weaponDamage = getType.getDamageVsEntity() - 4;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		par3List.add("Hammer Class");
		par3List.add("\u00a79Fire Element");
		par3List.add("\u00a72Siege Damage");
	}

	

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = par1IconRegister
				.registerIcon(MHFCReference.weapon_hm_rathalos_icon);
	}

	public float getDamageVsEntity(Entity entity) {

		return weaponDamage;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase ent,
			EntityLivingBase player) {
		player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 80, 1));
		float damage = 0.0f;
		player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 40, 8));
		ent.setFire(1);
		if (rand.nextInt() == 40) {
			// damage = 232;
		}

		DamageSource dmgSource = DamageSource
				.causePlayerDamage((EntityPlayer) player);
		ent.attackEntityFrom(dmgSource, damage);

		return true;
	}

}
