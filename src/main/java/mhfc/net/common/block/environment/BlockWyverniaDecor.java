package mhfc.net.common.block.environment;

import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class BlockWyverniaDecor extends BlockBush {
	public BlockWyverniaDecor(Material material) {
		super(material);
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return true;
	}
}
