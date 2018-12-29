package mhfc.net.common.block.decoration;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.AbstractSubTypedBlock;
import mhfc.net.common.block.decoration.BlockLeaves.BlockLeavesSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLeaves extends AbstractSubTypedBlock<BlockLeavesSubType> {

	protected Minecraft setting;

	public static enum BlockLeavesSubType implements SubTypedItem.SubTypeEnum<Block> {
		LEAVES1("l1", ResourceInterface.block_leaves1_name, 0),
		LEAVES2("l2", ResourceInterface.block_leaves2_name, 1),
		LEAVES3("l3", ResourceInterface.block_leaves3_name, 2),
		LEAVES4("l4", ResourceInterface.block_leaves4_name, 3),
		LEAVES5("l5", ResourceInterface.block_leaves5_name, 4),
		LEAVES6("l6", ResourceInterface.block_leaves6_name, 5),
		LEAVES7("l7", ResourceInterface.block_leaves7_name, 6),
		LEAVES8("l8", ResourceInterface.block_leaves8_name, 7),
		LEAVES9("l9", ResourceInterface.block_leaves9_name, 8),
		LEAVES10("l10", ResourceInterface.block_leaves10_name, 9);

		public final String registryName;
		public final String unlocalizedName;
		public int metadata;

		private BlockLeavesSubType(String name, String unlocalized, int metadata) {
			this.registryName = name;
			this.unlocalizedName = unlocalized;
			this.metadata = metadata;
		}

		@Override
		public String getName() {
			return registryName;
		}

		@Override
		public String getUnlocalizedName() {
			return unlocalizedName;
		}

		@Override
		public Block getBaseItem() {
			return MHFCBlockRegistry.getRegistry().mhfcblockleaves;
		}
		
		public int getMetadata() {
			return this.metadata;
		}
	}

	public static final PropertyEnum<BlockLeavesSubType> PROPERTY = create(BlockLeavesSubType.class);
	public BlockLeaves() {
		super(PROPERTY, Material.LEAVES);
		setTranslationKey(ResourceInterface.block_wyvernialeaves_basename);
		setHardness(0.2f);
		setSoundType(SoundType.PLANT);
		setLightOpacity(1);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTY);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		/*	if (!setting.gameSettings.fancyGraphics) {
				return BlockRenderLayer.SOLID;
			}*/
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (BlockLeaves.BlockLeavesSubType subtype : BlockLeaves.BlockLeavesSubType.values())
		{
	            items.add(new ItemStack(this, 1, subtype.getMetadata()));
		}
    }


}
