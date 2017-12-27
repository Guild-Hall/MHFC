package mhfc.net.common.block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class Decoration extends BlockBush {
	public Decoration(Material material) {
		super(material);
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return true;
	}
}
