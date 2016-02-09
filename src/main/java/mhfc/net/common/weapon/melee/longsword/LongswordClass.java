package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponClassingHelper;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.ComponentMelee;
import mhfc.net.common.weapon.ComponentMelee.WeaponSpecs;
import mhfc.net.common.weapon.melee.WeaponMelee;
import mhfc.net.common.weapon.melee.iWeaponReach;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class LongswordClass extends WeaponMelee implements iWeaponReach {

	public String lsLocal = "longsword_";

	public LongswordClass(ToolMaterial getType) {
		super(new ComponentMelee(WeaponSpecs.LONGSWORD, getType));
		getWeaponDescription(MHFCWeaponClassingHelper.longswordname);
		setTextureName(MHFCReference.weapon_ls_default_icon);
		getWeaponTable(4, -12, 0);

	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase ent,
			EntityLivingBase player) {
		// player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30, 1));
		if (poisontype)
			ent.addPotionEffect(new PotionEffect(Potion.poison.id, 30,
					amplified));
		if (firetype)
			ent.setFire(1);
		meleecomp.hitEntity(stack, ent, player);
		return true;
	}

	@Override
	public float getExtendedReach(World world, EntityLivingBase living, ItemStack itemstack) {
		return 4;
	}
}
