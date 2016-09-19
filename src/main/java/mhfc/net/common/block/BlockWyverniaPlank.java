package mhfc.net.common.block;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.BlockWyverniaPlank.WyverniaPlankSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.util.SubTypedItem;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockWyverniaPlank extends AbstractSubTypedBlock<WyverniaPlankSubType> {
	public static enum WyverniaPlankSubType implements SubTypedItem.SubTypeEnum<Block> {
		CALFER(MHFCReference.block_calfer_name),
		DIREWOOD(MHFCReference.block_direwood_name),
		GRAND_IFOLIA(MHFCReference.block_grandifolia_name),
		MAVEN(MHFCReference.block_maven_name),
		NEGUNDO(MHFCReference.block_negundo_name),
		PALMER(MHFCReference.block_palmer_name),
		RADEL(MHFCReference.block_radel_name),
		SANDY(MHFCReference.block_sandy_name),
		TILIA(MHFCReference.block_tilia_name);

		public final String name;

		private WyverniaPlankSubType(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Block getBaseItem() {
			return MHFCBlockRegistry.getRegistry().mhfcblockplanks;
		}
	}

	public static final PropertyEnum<WyverniaPlankSubType> PROPERTY = create(WyverniaPlankSubType.class);

	public BlockWyverniaPlank() {
		super(PROPERTY, Material.WOOD);
		setUnlocalizedName(MHFCReference.block_wyverniaplank_basename);
		setHardness(0.6f);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTY);
	}
}
