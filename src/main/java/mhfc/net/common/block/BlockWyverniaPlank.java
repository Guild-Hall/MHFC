package mhfc.net.common.block;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.BlockWyverniaPlank.WyverniaPlankSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockWyverniaPlank extends AbstractSubTypedBlock<WyverniaPlankSubType> {
	public static enum WyverniaPlankSubType implements SubTypedItem.SubTypeEnum<Block> {
		CALFER(Libraries.block_calfer_name),
		DIREWOOD(Libraries.block_direwood_name),
		GRAND_IFOLIA(Libraries.block_grandifolia_name),
		MAVEN(Libraries.block_maven_name),
		NEGUNDO(Libraries.block_negundo_name),
		PALMER(Libraries.block_palmer_name),
		RADEL(Libraries.block_radel_name),
		SANDY(Libraries.block_sandy_name),
		TILIA(Libraries.block_tilia_name);

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
		setUnlocalizedName(Libraries.block_wyverniaplank_basename);
		setHardness(0.6f);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTY);
	}
}
