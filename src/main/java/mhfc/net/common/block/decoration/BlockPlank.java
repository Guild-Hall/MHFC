package mhfc.net.common.block.decoration;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.AbstractSubTypedBlock;
import mhfc.net.common.block.decoration.BlockPlank.WyverniaPlankSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class BlockPlank extends AbstractSubTypedBlock<WyverniaPlankSubType> {
	public static enum WyverniaPlankSubType implements SubTypedItem.SubTypeEnum<Block> {
		CALFER("calfer", ResourceInterface.block_calfer_name, 0 ),
		DIREWOOD("direwood", ResourceInterface.block_direwood_name, 1),
		GRAND_IFOLIA("ifolia", ResourceInterface.block_grandifolia_name, 2),
		MAVEN("maven", ResourceInterface.block_maven_name, 3),
		NEGUNDO("negundo", ResourceInterface.block_negundo_name, 4),
		PALMER("palmer", ResourceInterface.block_palmer_name, 5),
		RADEL("radel", ResourceInterface.block_radel_name, 6),
		SANDY("sandy", ResourceInterface.block_sandy_name, 7),
		TILIA("tilia", ResourceInterface.block_tilia_name, 8);

		public final String registryName;
		public final String unlocalizedName;
		public int metadata;

		private WyverniaPlankSubType(String name, String unlocalized, int metadata) {
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
			return MHFCBlockRegistry.getRegistry().mhfcblockplanks;
		}
		
		public int getMetadata() {
			return this.metadata;
		}
	}

	public static final PropertyEnum<WyverniaPlankSubType> PROPERTY = create(WyverniaPlankSubType.class);

	public BlockPlank() {
		super(PROPERTY, Material.WOOD);
		setTranslationKey(ResourceInterface.block_wyverniaplank_basename);
		setHardness(0.6f);
		setCreativeTab(MHFCMain.mhfctabs);
		setSoundType(SoundType.WOOD);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTY);
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (BlockPlank.WyverniaPlankSubType subtype : BlockPlank.WyverniaPlankSubType.values())
		{
	            items.add(new ItemStack(this, 1, subtype.getMetadata()));
		}
    }
}
