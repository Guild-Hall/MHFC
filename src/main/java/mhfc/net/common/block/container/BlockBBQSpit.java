package mhfc.net.common.block.container;

import mhfc.net.MHFCMain;
import mhfc.net.common.tile.TileBBQSpit;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBBQSpit extends BlockContainer {

	public BlockBBQSpit() {
		super(Material.IRON);
		setUnlocalizedName(MHFCReference.block_bbqspit_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileBBQSpit();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
