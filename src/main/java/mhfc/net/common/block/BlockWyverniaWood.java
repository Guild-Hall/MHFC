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
		CALFER("calfer", ResourceInterface.block_log_calfer_name),
		DIREWOOD("direwood", ResourceInterface.block_log_direwood_name),
		GRAND_IFOLIA("ifolia", ResourceInterface.block_log_grandifolia_name),
		MAVEN("maven", ResourceInterface.block_log_maven_name),
		NEGUNDO("negundo", ResourceInterface.block_log_negundo_name),
		PALMER("palmer", ResourceInterface.block_log_palmer_name),
		RADEL("radel", ResourceInterface.block_log_radel_name),
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
