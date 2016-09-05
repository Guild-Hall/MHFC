package mhfc.net.common.block;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.BlockWyverniaRock.WyverniaRockSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockWyverniaRock extends AbstractSubTypedBlock<WyverniaRockSubType> {
	public static enum WyverniaRockSubType implements SubTypedItem.SubTypeEnum<Block> {
		AUVEL(MHFCReference.block_auvel_name),
		CRADLE(MHFCReference.block_cradle_name),
		TACREN(MHFCReference.block_tacren_name);

		public final String name;

		private WyverniaRockSubType(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Block getBaseItem() {
			return MHFCBlockRegistry.getRegistry().mhfcblockrocks;
		}
	}

	public BlockWyverniaRock() {
		super(WyverniaRockSubType.class, Material.ROCK);
		setUnlocalizedName(MHFCReference.block_wyverniarock_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(1.1f);
	}
}
