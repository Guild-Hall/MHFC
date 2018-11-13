package mhfc.net.common.block.decoration;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.AbstractSubTypedBlock;
import mhfc.net.common.block.decoration.BlockWood.WyverniaLogSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class BlockWood extends AbstractSubTypedBlock<WyverniaLogSubType> {
	public static enum WyverniaLogSubType implements SubTypedItem.SubTypeEnum<Block> {
		CALFER("calfer", ResourceInterface.block_log_calfer_name), // 0
		DIREWOOD("direwood", ResourceInterface.block_log_direwood_name), //1
		GRAND_IFOLIA("ifolia", ResourceInterface.block_log_grandifolia_name),//2
		MAVEN("maven", ResourceInterface.block_log_maven_name),//3
		NEGUNDO("negundo", ResourceInterface.block_log_negundo_name),//4
		PALMER("palmer", ResourceInterface.block_log_palmer_name),//5
		RADEL("radel", ResourceInterface.block_log_radel_name),//6
		SANDY("sandy", ResourceInterface.block_log_sandy_name),
		TILIA("tilia", ResourceInterface.block_log_tilia_name);

		public final String registryName;
		public final String unlocalizedName;

		private WyverniaLogSubType(String name, String unlocalized) {
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
			return MHFCBlockRegistry.getRegistry().mhfcblockwood;
		}
	}

	public static final PropertyEnum<WyverniaLogSubType> PROPERTY = create(WyverniaLogSubType.class);

	public BlockWood() {
		super(PROPERTY, Material.WOOD);
		setTranslationKey(ResourceInterface.block_wyvernialog_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setSoundType(SoundType.WOOD);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTY);
	}

	@Override
	public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos) {
		return true;
	}

}
