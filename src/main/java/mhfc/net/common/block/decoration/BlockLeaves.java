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
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLeaves extends AbstractSubTypedBlock<BlockLeavesSubType> {

	protected Minecraft setting;

	public static enum BlockLeavesSubType implements SubTypedItem.SubTypeEnum<Block> {
		LEAVES1("l1", ResourceInterface.block_leaves1_name),
		LEAVES2("l2", ResourceInterface.block_leaves2_name),
		LEAVES3("l3", ResourceInterface.block_leaves3_name),
		LEAVES4("l4", ResourceInterface.block_leaves4_name),
		LEAVES5("l5", ResourceInterface.block_leaves5_name),
		LEAVES6("l6", ResourceInterface.block_leaves6_name),
		LEAVES7("l7", ResourceInterface.block_leaves7_name),
		LEAVES8("l8", ResourceInterface.block_leaves8_name),
		LEAVES9("l9", ResourceInterface.block_leaves9_name),
		LEAVES10("l10", ResourceInterface.block_leaves10_name);

		public final String registryName;
		public final String unlocalizedName;

		private BlockLeavesSubType(String name, String unlocalized) {
			this.registryName = name;
			this.unlocalizedName = unlocalized;
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
	}

	public static final PropertyEnum<BlockLeavesSubType> PROPERTY = create(BlockLeavesSubType.class);
	public BlockLeaves() {
		super(PROPERTY, Material.LEAVES);
		setUnlocalizedName(ResourceInterface.block_wyvernialeaves_basename);
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
	public BlockRenderLayer getBlockLayer() {
		/*	if (!setting.gameSettings.fancyGraphics) {
				return BlockRenderLayer.SOLID;
			}*/
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}




}
