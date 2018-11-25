package mhfc.net.common.block.quest;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCContainerRegistry;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockQuestBoardNew extends Block {

	public BlockQuestBoardNew() {
		super(Material.ROCK);
		setTranslationKey(ResourceInterface.block_questboardnew_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	@Override
	public boolean onBlockActivated(
			World world,
			BlockPos pos,
			IBlockState state,
			EntityPlayer player,
			EnumHand hand,
			EnumFacing side,
			float hitX,
			float hitY,
			float hitZ) {
		if (!player.isSneaking()) {
			player.openGui(
					MHFCMain.instance(),
					MHFCContainerRegistry.gui_questboard_id,
					world,
					pos.getX(),
					pos.getY(),
					pos.getZ());
			return true;
		}
		return false;
	}

}
