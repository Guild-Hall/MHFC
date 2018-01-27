package mhfc.net.common.block.decoration;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockQuickSand extends Block {
	private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0, 0, 0, 1, 0.5, 1);

	public BlockQuickSand() {
		super(Material.SAND);
		setUnlocalizedName(ResourceInterface.block_quicksand_name);
		setHardness(1.3F);
		setResistance(2.0F);
		setCreativeTab(MHFCMain.mhfctabs);
		setSoundType(SoundType.SAND);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		entity.setInWeb();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDS;
	}

}
