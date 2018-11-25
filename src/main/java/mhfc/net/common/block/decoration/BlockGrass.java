package mhfc.net.common.block.decoration;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockGrass extends Block {

	public BlockGrass() {
		super(Material.GRASS);
		setTranslationKey(ResourceInterface.block_wyveriangrass_name);
		setHardness(0.8F);
		setCreativeTab(MHFCMain.mhfctabs);
		this.setSoundType(SoundType.PLANT);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}



}
