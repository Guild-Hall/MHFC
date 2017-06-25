package mhfc.net.common.world.types;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.EmptyArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IExtendedConfiguration;
import mhfc.net.common.worldedit.ClipboardFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VillagePokeType extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation(
			"mhfc:schematics/village_poke.schematic");
	public static final VillagePokeType INSTANCE = new VillagePokeType();

	public static class VillagePokeArea extends EmptyArea {
		public VillagePokeArea(World world, AreaConfiguration config) {
			super(world, config);
		}

		@Override
		protected BlockPos getPlayerSpawnPosition() {
			return new BlockPos(10, 0, 22);
		}

		@Override
		protected BlockPos getMonsterSpawnPosition() {
			return new BlockPos(10, 0, 10);
		}
	}

	private VillagePokeType() {
		super(schematicLocation, ClipboardFormats.NATIVE_SCHEMATIC);
	}

	@Override
	public String getUnlocalizedName() {
		return ResourceInterface.area_pokevillage_name;
	}

	@Override
	public IArea provideForLoading(World world, AreaConfiguration config) {
		return new VillagePokeArea(world, config);
	}

	@Override
	public IExtendedConfiguration configForLoading() {
		return IExtendedConfiguration.EMPTY;
	}

}
