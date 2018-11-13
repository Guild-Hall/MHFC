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

public class BlockPlank extends AbstractSubTypedBlock<WyverniaPlankSubType> {
	public static enum WyverniaPlankSubType implements SubTypedItem.SubTypeEnum<Block> {
		CALFER("calfer", ResourceInterface.block_calfer_name),
		DIREWOOD("direwood", ResourceInterface.block_direwood_name),
		GRAND_IFOLIA("ifolia", ResourceInterface.block_grandifolia_name),
		MAVEN("maven", ResourceInterface.block_maven_name),
		NEGUNDO("negundo", ResourceInterface.block_negundo_name),
		PALMER("palmer", ResourceInterface.block_palmer_name),
		RADEL("radel", ResourceInterface.block_radel_name),
		SANDY("sandy", ResourceInterface.block_sandy_name),
		TILIA("tilia", ResourceInterface.block_tilia_name);

		public final String registryName;
		public final String unlocalizedName;

		private WyverniaPlankSubType(String name, String unlocalized) {
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
			return MHFCBlockRegistry.getRegistry().mhfcblockplanks;
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
}
