package mhfc.heltrato.common.block.container;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.tile.TileBBQSpit;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBBQSpit extends BlockContainer {

	public BlockBBQSpit() {
		super(Material.iron);
		setBlockName(MHFCReference.block_bbqspit_name);
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

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister
				.registerIcon(MHFCReference.block_bbqspit_icon);
	}

}
