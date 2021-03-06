package mhfc.net.common.block.decoration;

import mhfc.net.MHFCMain;
import mhfc.net.common.block.AbstractSubTypedBlock;
import mhfc.net.common.block.decoration.BlockOres.WyverniaOreSubType;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockOres extends AbstractSubTypedBlock<WyverniaOreSubType> {
	public static enum WyverniaOreSubType implements SubTypedItem.SubTypeEnum<Block> {
		ARMOR_SPHERE_ORE("armor_sphere", ResourceInterface.block_orearmorsphere_name),
		ARMOR_SPHERE_PLUS_ORE("armor_sphere_plus", ResourceInterface.block_orearmorsphereplus_name),
		CARBALITE_ORE("carbalite", ResourceInterface.block_orecarbalite_name),
		DRAGONITE_ORE("dragonite", ResourceInterface.block_oredragonite_name),
		ELTALITE_ORE("eltalite", ResourceInterface.block_oreeltalite_name),
		MACHALITE_ORE("machalite", ResourceInterface.block_oremachalite_name),
		FURUKURAITO_ORE("furukaito", ResourceInterface.block_orefurukuraito_name);

		public final String registryName;
		public final String unlocalizedName;

		private WyverniaOreSubType(String name, String unlocalized) {
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
			return MHFCBlockRegistry.getRegistry().mhfcblockore;
		}
	}

	public static final PropertyEnum<WyverniaOreSubType> PROPERTY = create(WyverniaOreSubType.class);

	public BlockOres() {
		super(PROPERTY, Material.ROCK);
		setUnlocalizedName(ResourceInterface.block_ores_basename);
		setCreativeTab(MHFCMain.mhfctabs);
		setHardness(2.0f);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PROPERTY);
	}

	@Override
	public boolean removedByPlayer(
			IBlockState state,
			World world,
			BlockPos pos,
			EntityPlayer player,
			boolean willHarvest) {
		if (!willHarvest) {
			// The block is simply destroyed
			return super.removedByPlayer(state, world, pos, player, willHarvest);
		}
		// The block is harvested. super.removedByPlayer would also set the block to air
		return true;
	}
}
