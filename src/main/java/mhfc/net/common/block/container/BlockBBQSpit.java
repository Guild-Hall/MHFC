package mhfc.net.common.block.container;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.tile.TileBBQSpit;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBBQSpit extends BlockContainer {

	public BlockBBQSpit() {
		super(Material.IRON);
		setUnlocalizedName(ResourceInterface.block_bbqspit_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileBBQSpit();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

}
