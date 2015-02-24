package mhfc.net.common.block;

import mhfc.net.MHFCMain;
import mhfc.net.common.tile.TileQuestBoard;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockQuestBoard extends BlockContainer {

	public BlockQuestBoard() {
		super(Material.ground);
		setBlockName(MHFCReference.block_questBoard_name);
		setHardness(3.0f);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World var1, int var2, int var3, int var4,
			EntityPlayer player, int var6, float var7, float var8, float var9) {
		if (!player.isSneaking()) {
			player.openGui(MHFCMain.instance, MHFCReference.gui_questboard_id,
					var1, var2, var3, var4);
			return true;
		}
		return false;
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		// FIXME: supply proxy texture?
		// blockIcon = par1IconRegister
		// .registerIcon(MHFCReference.block_questBoard_icon);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileQuestBoard();
	}

}
