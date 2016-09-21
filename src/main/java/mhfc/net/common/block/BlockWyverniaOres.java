package mhfc.net.common.block;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.BlockWyverniaOres.WyverniaOreSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class BlockWyverniaOres extends AbstractSubTypedBlock<WyverniaOreSubType> {
	public static enum WyverniaOreSubType implements SubTypedItem.SubTypeEnum<Block> {
		ARMOR_SPHERE_ORE(Libraries.block_orearmorsphere_name),
		ARMOR_SPHERE_PLUS_ORE(Libraries.block_orearmorsphereplus_name),
		CARBALITE_ORE(Libraries.block_orecarbalite_name),
		DRAGONITE_ORE(Libraries.block_oredragonite_name),
		ELTALITE_ORE(Libraries.block_oreeltalite_name),
		MACHALITE_ORE(Libraries.block_oremachalite_name),
		FURUKURAITO_ORE(Libraries.block_orefurukuraito_name);

		public final String name;

		private WyverniaOreSubType(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Block getBaseItem() {
			return MHFCBlockRegistry.getRegistry().mhfcblockore;
		}
	}

	public static final PropertyEnum<WyverniaOreSubType> PROPERTY = create(WyverniaOreSubType.class);

	public BlockWyverniaOres() {
		super(PROPERTY, Material.ROCK);
		setUnlocalizedName(Libraries.block_ores_basename);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTY);
	}

}
