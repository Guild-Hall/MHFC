package mhfc.heltrato.common.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WideDesertGen extends WorldGenerator
{
	protected Block[] GetValidSpawnBlocks()
	{
		return new Block[]
		{
			Blocks.stone,
		};
	}

	public boolean LocationIsValidSpawn(World world, int x, int y, int z)
	{
		int distanceToAir = 0;
		Block checkBlock = world.getBlock(x, y, z);

		while (checkBlock != Blocks.air)
		{
			distanceToAir++;
			checkBlock = world.getBlock(x, y + distanceToAir, z);
		}

		if (distanceToAir > 1)
		{
			return false;
		}

		y += distanceToAir - 1;

		Block block = world.getBlock(x, y, z);
		Block blockAbove = world.getBlock(x, y + 1, z);
		Block blockBelow = world.getBlock(x, y - 1, z);

		for (Block i : GetValidSpawnBlocks())
		{
			if (blockAbove != Blocks.air)
			{
				return false;
			}
			if (block == i)
			{
				return true;
			}
			else if (block == Blocks.snow_layer && blockBelow == i)
			{
				return true;
			}
			else if (block.getMaterial() == Material.plants && blockBelow == i)
			{
				return true;
			}
		}
		return false;
	}

	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		if(!LocationIsValidSpawn(world, x, y, z) || !LocationIsValidSpawn(world, x + 4, y, z) || !LocationIsValidSpawn(world, x + 4, y, z + 4) || !LocationIsValidSpawn(world, x, y, z + 4))
		{
			return false;
		}

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
}