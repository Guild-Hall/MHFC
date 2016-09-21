package mhfc.net.common.block;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.BlockWyverniaOreBlock.WyverniaOreBlockSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockWyverniaOreBlock extends AbstractSubTypedBlock<WyverniaOreBlockSubType> {
	public static enum WyverniaOreBlockSubType implements SubTypedItem.SubTypeEnum<Block> {
		MACHALITE(ResourceInterface.block_machalite_name),
		CARBALITE(ResourceInterface.block_carbalite_name),
		ELTALITE(ResourceInterface.block_eltalite_name),
		DRAGONITE(ResourceInterface.block_dragonite_name);

		public final String name;

		private WyverniaOreBlockSubType(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Block getBaseItem() {
			return MHFCBlockRegistry.getRegistry().mhfcblockoreblocks;
		}
	}

	public static final PropertyEnum<WyverniaOreBlockSubType> PROPERTY = create(WyverniaOreBlockSubType.class);

	public BlockWyverniaOreBlock() {
		super(PROPERTY, Material.ROCK);
		setUnlocalizedName(ResourceInterface.block_oreblock_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTY);
	}
}
