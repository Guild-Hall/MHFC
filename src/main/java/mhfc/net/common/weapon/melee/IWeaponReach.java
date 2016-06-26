package mhfc.net.common.weapon.melee;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IWeaponReach
{
	public float getExtendedReach(World world, EntityLivingBase living, ItemStack itemstack);
}
