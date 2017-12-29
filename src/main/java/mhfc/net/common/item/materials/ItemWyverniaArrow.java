package mhfc.net.common.item.materials;

import mhfc.net.MHFCMain;
import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWyverniaArrow extends Item {

	public ItemWyverniaArrow() {
		super();
		setUnlocalizedName(ResourceInterface.item_arrow0_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	public EntityWyverniaArrow createArrow(
			World worldIn,
			ItemStack stack,
			EntityLivingBase shooter,
			float force) {
		return new EntityWyverniaArrow(worldIn, shooter, force);
	}

}
