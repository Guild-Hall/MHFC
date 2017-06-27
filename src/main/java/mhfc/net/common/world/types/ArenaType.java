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

public class ArenaType extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation("mhfc:schematics/Arena.schematic");

	public static final ArenaType INSTANCE = new ArenaType();

	private static class Area extends EmptyArea {
		public Area(World world, AreaConfiguration config) {
			super(world, config);
		}

		@Override
		protected BlockPos getPlayerSpawnPosition() {
			return new BlockPos(54, -1, 11);
		}

		@Override
		protected BlockPos getMonsterSpawnPosition() {
			return new BlockPos(50, -1, 62);
		}
	}

	private ArenaType() {
		super(ArenaType.schematicLocation, ClipboardFormats.NATIVE_SCHEMATIC);
	}

	@Override
	public String getUnlocalizedName() {
		return ResourceInterface.area_arena_name;
	}

	@Override
	public IArea provideForLoading(World world, AreaConfiguration configuration) {
		return new Area(world, configuration);
	}

	@Override
	public IExtendedConfiguration configForLoading() {
		return IExtendedConfiguration.EMPTY;
	}

}
