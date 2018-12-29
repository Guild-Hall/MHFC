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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class BlockOreBlock extends AbstractSubTypedBlock<WyverniaOreBlockSubType> {
	public static enum WyverniaOreBlockSubType implements SubTypedItem.SubTypeEnum<Block> {
		MACHALITE("machalite", ResourceInterface.block_machalite_name, 0),
		CARBALITE("carbalite", ResourceInterface.block_carbalite_name, 1),
		ELTALITE("eltalite", ResourceInterface.block_eltalite_name, 2),
		DRAGONITE("dragonite", ResourceInterface.block_dragonite_name,3);

		public final String registryName;
		public final String unlocalizedName;
		public int metadata;

		private WyverniaOreBlockSubType(String name, String unlocalized, int metadata) {
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
			return MHFCBlockRegistry.getRegistry().mhfcblockoreblocks;
		}
		
		public int getMetadata() {
			return this.metadata;
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

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (BlockOreBlock.WyverniaOreBlockSubType subtype : BlockOreBlock.WyverniaOreBlockSubType.values())
		{
	            items.add(new ItemStack(this, 1, subtype.getMetadata()));
		}
    }

}
