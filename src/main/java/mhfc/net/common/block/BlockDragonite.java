package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockDragonite extends Block {

	public BlockDragonite() {
		super(Material.rock);
		setBlockName(MHFCReference.block_dragonite_name);
		setBlockTextureName(MHFCReference.tex_block_dragonite);
		setHardness(1.4F);
		setResistance(1.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_,
			float p_149727_8_, float p_149727_9_) {
		world.setBlock(x + 0, y + 0, z + 0, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 0, z + 0, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 0, z + 0, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 0, z + 0, Blocks.sandstone, 0, 3);
		world.setBlock(x + 4, y + 0, z + 0, Blocks.sandstone, 0, 3);
		world.setBlock(x + 0, y + 0, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 0, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 0, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 0, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 4, y + 0, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 0, y + 0, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 0, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 0, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 0, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 4, y + 0, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 0, y + 0, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 0, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 0, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 0, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 4, y + 0, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 0, y + 0, z + 4, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 0, z + 4, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 0, z + 4, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 0, z + 4, Blocks.sandstone, 0, 3);
		world.setBlock(x + 4, y + 0, z + 4, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 1, z + 0, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 1, z + 0, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 1, z + 0, Blocks.sandstone, 0, 3);
		world.setBlock(x + 4, y + 1, z + 0, Blocks.sandstone, 0, 3);
		world.setBlock(x + 0, y + 1, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 1, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 1, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 1, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 4, y + 1, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 0, y + 1, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 1, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 1, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 1, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 4, y + 1, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 0, y + 1, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 1, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 1, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 1, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 4, y + 1, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 0, y + 1, z + 4, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 1, z + 4, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 1, z + 4, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 1, z + 4, Blocks.sandstone, 0, 3);
		world.setBlock(x + 4, y + 1, z + 4, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 2, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 2, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 2, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 2, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 2, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 2, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 2, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 2, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 2, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 3, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 3, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 3, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 3, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 3, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 3, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 3, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 3, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 3, z + 3, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 4, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 4, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 3, y + 4, z + 1, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 4, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 4, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 1, y + 5, z + 2, Blocks.sandstone, 0, 3);
		world.setBlock(x + 2, y + 5, z + 2, Blocks.sandstone, 0, 3);
		return true;
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
