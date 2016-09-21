package mhfc.net.common.block;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.BlockWyverniaWood.WyverniaLogSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockWyverniaWood extends AbstractSubTypedBlock<WyverniaLogSubType> {
	public static enum WyverniaLogSubType implements SubTypedItem.SubTypeEnum<Block> {
		CALFER(ResourceInterface.block_log_calfer_name),
		DIREWOOD(ResourceInterface.block_log_direwood_name),
		GRAND_IFOLIA(ResourceInterface.block_log_grandifolia_name),
		MAVEN(ResourceInterface.block_log_maven_name),
		NEGUNDO(ResourceInterface.block_log_negundo_name),
		PALMER(ResourceInterface.block_log_palmer_name),
		RADEL(ResourceInterface.block_log_radel_name),
		SANDY(ResourceInterface.block_log_sandy_name),
		TILIA(ResourceInterface.block_log_tilia_name);

		public final String name;

		private WyverniaLogSubType(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Block getBaseItem() {
			return MHFCBlockRegistry.getRegistry().mhfcblockwood;
		}
	}

	public static final PropertyEnum<WyverniaLogSubType> PROPERTY = create(WyverniaLogSubType.class);

	public BlockWyverniaWood() {
		super(PROPERTY, Material.WOOD);
		setUnlocalizedName(ResourceInterface.block_wyvernialog_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTY);
	}

}
