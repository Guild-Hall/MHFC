package mhfc.net.common.block.decoration;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.Decoration;
import mhfc.net.common.block.IBlockVarianted;
import mhfc.net.common.block.ISubTypedBlock;
import mhfc.net.common.block.decoration.BlockFlower.WyverniaFlowerSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFlower extends Decoration
		implements
		ISubTypedBlock<WyverniaFlowerSubType>,
		IBlockVarianted {
	public static enum WyverniaFlowerSubType implements SubTypedItem.SubTypeEnum<Block> {
		CARNCASE("carncase", ResourceInterface.block_carncase_name, 0),
		FELRON("felron", ResourceInterface.block_felron_name, 1),
		ORCTAL("orctal", ResourceInterface.block_orctal_name, 2),
		PENO("peno", ResourceInterface.block_peno_name, 3),
		SHRINE("shrine", ResourceInterface.block_shrine_name, 4),
		SPINDEL("spindel", ResourceInterface.block_spindel_name, 5),
		BERPIS("berpis", ResourceInterface.block_berpis_name, 6),
		CONCAVE("concave", ResourceInterface.block_concave_name, 7),
		DELPHI("delphi", ResourceInterface.block_delphi_name, 8),
		EMBER("ember", ResourceInterface.block_ember_name, 9),
		GRESHA("gresha", ResourceInterface.block_gresha_name, 10),
		MOWAL("mowal", ResourceInterface.block_mowal_name, 11),
		NEPTIA("neptia", ResourceInterface.block_neptia_name, 12),
		ROY("roy", ResourceInterface.block_roy_name, 13),
		SAMPA("sampa", ResourceInterface.block_sampa_name, 14),
		SILON("silon", ResourceInterface.block_silon_name, 15);

		public final String registryName;
		public final String name;
		public int metadata;

		private WyverniaFlowerSubType(String registryName, String name, int metadata) {
			this.registryName = registryName;
			this.name = name;
			this.metadata = metadata;
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
			return MHFCBlockRegistry.getRegistry().mhfcblockflowers;
		}
		
		@SideOnly(Side.CLIENT)
		public void getSubItems(Item item, List<ItemStack> list) {
			blockTrait.getSubItems(item, list);
		}

		public int getMetadata() {
			return this.metadata;
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
		setSoundType(SoundType.PLANT);
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
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		
		for(BlockFlower.WyverniaFlowerSubType subtype : BlockFlower.WyverniaFlowerSubType.values()) {
			 items.add(new ItemStack(this, 1, subtype.getMetadata()));
		}
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
