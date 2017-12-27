package mhfc.net.common.block.decoration;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.Decoration;
import mhfc.net.common.block.IBlockVarianted;
import mhfc.net.common.block.ISubTypedBlock;
import mhfc.net.common.block.decoration.BlockFlower.WyverniaFlowerSubType;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockFlower extends Decoration
		implements
		ISubTypedBlock<WyverniaFlowerSubType>,
		IBlockVarianted {
	public static enum WyverniaFlowerSubType implements SubTypedItem.SubTypeEnum<Block> {
		CARNCASE("carncase", ResourceInterface.block_carncase_name),
		FELRON("felron", ResourceInterface.block_felron_name),
		ORCTAL("orctal", ResourceInterface.block_orctal_name),
		PENO("peno", ResourceInterface.block_peno_name),
		SHRINE("shrine", ResourceInterface.block_shrine_name),
		SPINDEL("spindel", ResourceInterface.block_spindel_name),
		BERPIS("berpis", ResourceInterface.block_berpis_name),
		CONCAVE("concave", ResourceInterface.block_concave_name),
		DELPHI("delphi", ResourceInterface.block_delphi_name),
		EMBER("ember", ResourceInterface.block_ember_name),
		GRESHA("gresha", ResourceInterface.block_gresha_name),
		MOWAL("mowal", ResourceInterface.block_mowal_name),
		NEPTIA("neptia", ResourceInterface.block_neptia_name),
		ROY("roy", ResourceInterface.block_roy_name),
		SAMPA("sampa", ResourceInterface.block_sampa_name),
		SILON("silon", ResourceInterface.block_silon_name);

		public final String registryName;
		public final String name;

		private WyverniaFlowerSubType(String registryName, String name) {
			this.registryName = registryName;
			this.name = name;
		}

		@Override
		public String getName() {
			return registryName;
		}

		@Override
		public String getUnlocalizedName() {
			return name;
		}

		@Override
		public Block getBaseItem() {
			return null;//MHFCBlockRegistry.getRegistry().mhfcblockflowers;
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

	public BlockFlower() {
		super(Material.PLANTS);
		setUnlocalizedName(ResourceInterface.block_wyverniaflower_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(0.0f);
		setTickRandomly(true);
	}

	@Override
	public String getVariantName(IBlockState state) {
		return state.getValue(subtypeProperty).getName();
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
	public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list) {
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
		return SoundType.PLANT;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FLOWER_BOUNDS;
	}

	//FIXME: implement canSustainPlant for the correct ground blocks

}
