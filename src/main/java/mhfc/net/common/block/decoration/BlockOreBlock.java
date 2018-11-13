package mhfc.net.common.block.decoration;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.AbstractSubTypedBlock;
import mhfc.net.common.block.decoration.BlockOreBlock.WyverniaOreBlockSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockOreBlock extends AbstractSubTypedBlock<WyverniaOreBlockSubType> {
	public static enum WyverniaOreBlockSubType implements SubTypedItem.SubTypeEnum<Block> {
		MACHALITE("machalite", ResourceInterface.block_machalite_name),
		CARBALITE("carbalite", ResourceInterface.block_carbalite_name),
		ELTALITE("eltalite", ResourceInterface.block_eltalite_name),
		DRAGONITE("dragonite", ResourceInterface.block_dragonite_name);

		public final String registryName;
		public final String unlocalizedName;

		private WyverniaOreBlockSubType(String name, String unlocalized) {
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
			return MHFCBlockRegistry.getRegistry().mhfcblockoreblocks;
		}
	}

	public static final PropertyEnum<WyverniaOreBlockSubType> PROPERTY = create(WyverniaOreBlockSubType.class);

	public BlockOreBlock() {
		super(PROPERTY, Material.ROCK);
		setTranslationKey(ResourceInterface.block_oreblock_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTY);
	}
}
