package mhfc.net.common.block.decoration;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.AbstractSubTypedBlock;
import mhfc.net.common.block.decoration.BlockRock.WyverniaRockSubType;
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

public class BlockRock extends AbstractSubTypedBlock<WyverniaRockSubType> {
	public static enum WyverniaRockSubType implements SubTypedItem.SubTypeEnum<Block> {
		AUVEL("auvel", ResourceInterface.block_auvel_name, 0),
		CRADLE("cradle", ResourceInterface.block_cradle_name, 1),
		TACREN("tacren", ResourceInterface.block_tacren_name,2 );

		public final String registryName;
		public final String unlocalizedName;
		public final int metadata;
		
		private WyverniaRockSubType(String name, String unlocalized, int metadata) {
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
			return MHFCBlockRegistry.getRegistry().mhfcblockrocks;
		}
		
		public int getMetadata() {
			return this.metadata;
		}
	}

	public static final PropertyEnum<WyverniaRockSubType> PROPERTY = create(WyverniaRockSubType.class);

	public BlockRock() {
		super(PROPERTY, Material.ROCK);
		setTranslationKey(ResourceInterface.block_wyverniarock_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(1.1f);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTY);
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (BlockRock.WyverniaRockSubType subtype : BlockRock.WyverniaRockSubType.values())
		{
	            items.add(new ItemStack(this, 1, subtype.getMetadata()));
		}
    }
}
