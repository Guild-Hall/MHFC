package mhfc.net.common.block;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.BlockWyverniaWood.WyverniaLogSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockWyverniaWood extends AbstractSubTypedBlock<WyverniaLogSubType> {
	public static enum WyverniaLogSubType implements SubTypedItem.SubTypeEnum<Block> {
		CALFER(MHFCReference.block_log_calfer_name),
		DIREWOOD(MHFCReference.block_log_direwood_name),
		GRAND_IFOLIA(MHFCReference.block_log_grandifolia_name),
		MAVEN(MHFCReference.block_log_maven_name),
		NEGUNDO(MHFCReference.block_log_negundo_name),
		PALMER(MHFCReference.block_log_palmer_name),
		RADEL(MHFCReference.block_log_radel_name),
		SANDY(MHFCReference.block_log_sandy_name),
		TILIA(MHFCReference.block_log_tilia_name);

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

	public BlockWyverniaWood() {
		super(WyverniaLogSubType.class, Material.WOOD);
		setUnlocalizedName(MHFCReference.block_wyvernialog_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

}
