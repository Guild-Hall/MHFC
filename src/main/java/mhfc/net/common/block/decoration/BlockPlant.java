package mhfc.net.common.block.decoration;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.Decoration;
import mhfc.net.common.block.IBlockVarianted;
import mhfc.net.common.block.ISubTypedBlock;
import mhfc.net.common.block.decoration.BlockPlant.WyverniaPlantsSubType;
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

public class BlockPlant extends Decoration
		implements
		ISubTypedBlock<WyverniaPlantsSubType>,
		IBlockVarianted {
	public static enum WyverniaPlantsSubType implements SubTypedItem.SubTypeEnum<Block> {
		PLANT1("b1", ResourceInterface.block_plantb1_name),
		PLANT2("b2", ResourceInterface.block_plantb2_name),
		PLANT3("b3", ResourceInterface.block_plantb3_name),
		PLANT4("b4", ResourceInterface.block_plantb4_name),
		PLANT5("t1", ResourceInterface.block_plantt1_name),
		PLANT6("t2", ResourceInterface.block_plantt2_name),
		PLANT7("t3", ResourceInterface.block_plantt3_name),
		PLANT8("t4", ResourceInterface.block_plantt4_name);

		public final String registryName;
		public final String name;

		private WyverniaPlantsSubType(String registryName, String name) {
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
			return MHFCBlockRegistry.getRegistry().mhfcblockplant;
		}
	}

	protected final static AxisAlignedBB PLANT_BOUNDS;
	static {
		float f = 0.2F;
		PLANT_BOUNDS = new AxisAlignedBB(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
	}

	protected static final PropertyEnum<WyverniaPlantsSubType> subtypeProperty = PropertyEnum
			.create("variant", WyverniaPlantsSubType.class);
	protected static final SubTypedItem<Block, WyverniaPlantsSubType> blockTrait = new SubTypedItem<>(subtypeProperty);

	public BlockPlant() {
		super(Material.PLANTS);
		setUnlocalizedName(ResourceInterface.block_wyverniaplant_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(0.0f);
		setTickRandomly(true);
	}

	@Override
	public String getVariantName(IBlockState state) {
		return state.getValue(subtypeProperty).getName();
	}

	@Override
	public SubTypedItem<Block, WyverniaPlantsSubType> getBlockTrait() {
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
		return PLANT_BOUNDS;
	}


	//FIXME: implement canSustainPlant for the correct ground blocks

}
