package mhfc.net.common.block;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.BlockWyverniaFlower.WyverniaFlowerSubType;
import mhfc.net.common.block.environment.BlockWyverniaDecor;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWyverniaFlower extends BlockWyverniaDecor implements ISubTypedBlock<WyverniaFlowerSubType> {
	public static enum WyverniaFlowerSubType implements SubTypedItem.SubTypeEnum<Block> {
		CARNCASE(MHFCReference.block_carncase_name, MHFCReference.block_carncase_tex), //
		FELRON(MHFCReference.block_felron_name, MHFCReference.block_felron_tex), //
		ORCTAL(MHFCReference.block_orctal_name, MHFCReference.block_orctal_tex), //
		PENO(MHFCReference.block_peno_name, MHFCReference.block_peno_tex), //
		SHRINE(MHFCReference.block_shrine_name, MHFCReference.block_shrine_tex), //
		SPINDEL(MHFCReference.block_spindel_name, MHFCReference.block_spindel_tex);

		public final String name;
		public final String texture;

		private WyverniaFlowerSubType(String name, String tex) {
			this.name = name;
			this.texture = tex;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Block getBaseItem() {
			return MHFCBlockRegistry.getRegistry().mhfcblockflowers;
		}
	}

	private final SubTypedItem<Block, WyverniaFlowerSubType> blockTrait;

	public BlockWyverniaFlower() {
		super(Material.PLANTS);
		blockTrait = new SubTypedItem<>(WyverniaFlowerSubType.class);
		setUnlocalizedName(MHFCReference.block_wyverniaflower_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(0.0f);
		setTickRandomly(true);
		float f = 0.2F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
	}

	@Override
	public SubTypedItem<Block, WyverniaFlowerSubType> getBlockTrait() {
		return blockTrait;
	}

	@Override
	public SoundType getSoundType() {
		return SoundType.GROUND;
	}

	// TODO might have bugs.
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos) {
		return null;
	}

	@Override
	public int getRenderType() {
		return 1; // Magic number.
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
		blockTrait.getSubItems(item, list);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public boolean isValidPosition(World world, BlockPos pos, int metadata) {
		// TODO: getBlock()
		Block block = world.getBlockState(pos.down()).getBlock();
		return block == MHFCBlockRegistry.getRegistry().mhfcblockdirt
				|| block == MHFCBlockRegistry.getRegistry().mhfcblockgrass || block == Blocks.GRASS;
	}

}
