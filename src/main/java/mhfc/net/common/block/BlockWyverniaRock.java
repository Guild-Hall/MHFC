package mhfc.net.common.block;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.BlockWyverniaRock.WyverniaRockSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockWyverniaRock extends AbstractSubTypedBlock<WyverniaRockSubType> {
	public static enum WyverniaRockSubType implements SubTypedItem.SubTypeEnum<Block> {
		AUVEL(Libraries.block_auvel_name),
		CRADLE(Libraries.block_cradle_name),
		TACREN(Libraries.block_tacren_name);

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

	public static final PropertyEnum<WyverniaRockSubType> PROPERTY = create(WyverniaRockSubType.class);

	public BlockWyverniaRock() {
		super(PROPERTY, Material.ROCK);
		setUnlocalizedName(Libraries.block_wyverniarock_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(1.1f);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTY);
	}
}
