package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockWyverniaQuickSand extends Block {

	public BlockWyverniaQuickSand() {
		super(Material.sand);
		setBlockName(MHFCReference.block_quicksand_name);
		setBlockTextureName(MHFCReference.block_quicksand_tex);
		setHardness(1.3F);
		setResistance(2.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (entity instanceof EntityPlayer)
		entity.setInWeb();
		else{
			entity.motionX *= 0.1D;
			entity.motionZ *= 0.1D;
		}
			
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		if (world.getBlockMetadata(x, y, z) == 0)
		{
			float var5 = 1.7F;
			return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1 - var5, z + 1);
		}
		else return null;
	}


}
