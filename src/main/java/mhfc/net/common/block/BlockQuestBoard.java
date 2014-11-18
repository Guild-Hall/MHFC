package mhfc.net.common.block;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockQuestBoard extends Block {

	public BlockQuestBoard() {
		super(Material.ground);
		setBlockName(MHFCReference.block_questBoard_name);
		setHardness(3.0f);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	protected boolean canSilkHarvest() {
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
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		// FIXME blockIcon
	}

}
