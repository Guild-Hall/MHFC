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
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockWyverniaFlower extends BlockWyverniaDecor implements ISubTypedBlock<WyverniaFlowerSubType> {
	public static enum WyverniaFlowerSubType implements SubTypedItem.SubTypeEnum<Block> {
		CARNCASE(MHFCReference.block_carncase_name, MHFCReference.block_carncase_tex),
		FELRON(MHFCReference.block_felron_name, MHFCReference.block_felron_tex),
		ORCTAL(MHFCReference.block_orctal_name, MHFCReference.block_orctal_tex),
		PENO(MHFCReference.block_peno_name, MHFCReference.block_peno_tex),
		SHRINE(MHFCReference.block_shrine_name, MHFCReference.block_shrine_tex),
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

	protected final static AxisAlignedBB FLOWER_BOUNDS;
	static {
		float f = 0.2F;
		FLOWER_BOUNDS = new AxisAlignedBB(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
	}

	protected static final PropertyEnum<WyverniaFlowerSubType> subtypeProperty = PropertyEnum
			.create("variant", WyverniaFlowerSubType.class);
	protected static final SubTypedItem<Block, WyverniaFlowerSubType> blockTrait = new SubTypedItem<>(subtypeProperty);

	public BlockWyverniaFlower() {
		super(Material.PLANTS);
		setUnlocalizedName(MHFCReference.block_wyverniaflower_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(0.0f);
		setTickRandomly(true);
	}

	@Override
	public SubTypedItem<Block, WyverniaFlowerSubType> getBlockTrait() {
		return blockTrait;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, subtypeProperty);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
		getBlockTrait().getSubItems(item, list);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(subtypeProperty, getBlockTrait().getSubType(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return getBlockTrait().getMeta(state.getValue(subtypeProperty));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public SoundType getSoundType() {
		return SoundType.GROUND;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FLOWER_BOUNDS;
	}

	//FIXME: implement canSustainPlant for the correct ground blocks

}
