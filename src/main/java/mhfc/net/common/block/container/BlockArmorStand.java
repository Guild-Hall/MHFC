package mhfc.net.common.block.container;

import mhfc.net.MHFCMain;
import mhfc.net.common.tile.TileArmorStand;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockArmorStand extends BlockContainer {

	public BlockArmorStand() {
		super(Material.iron);
		setBlockBounds(1F / 16F * 1f, 0, 1F / 16F * 1f, 1F - 1F / 16F * 1f,
				1F - 1F / 16F * -10F, 1F - 1F / 16F * 1f);
		setBlockName(MHFCReference.block_armorstand_name);
		setHardness(1.4F);
		setCreativeTab(MHFCMain.mhfctabs);

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileArmorStand();
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
				.registerIcon(MHFCReference.icon_block_armorstand);
	}

}
