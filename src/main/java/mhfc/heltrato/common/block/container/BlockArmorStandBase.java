package mhfc.heltrato.common.block.container;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.tile.TileArmorStandBase;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockArmorStandBase extends BlockContainer {

	public BlockArmorStandBase() {
		super(Material.iron);

		setBlockBounds(1F / 16F * -0.05f, 0, 1F / 16F * -0.05f,
				1F - 1F / 16F * -0.05f, 1F - 1F / 16F * 14F,
				1F - 1F / 16F * -0.05f);
		setBlockName(MHFCReference.block_armorstandbase_name);
		setHardness(1.3F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileArmorStandBase();
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
		blockIcon = par1IconRegister.registerIcon(MHFCReference.block_armorstandbase_icon);
	}

}
