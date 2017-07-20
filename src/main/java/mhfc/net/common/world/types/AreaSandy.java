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

public class AreaSandy extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation(
			"mhfc:schematics/sandy.schematic");
	public static final AreaSandy INSTANCE = new AreaSandy();

	private static class Area extends EmptyArea {
		public Area(World world, AreaConfiguration config) {
			super(world, config);
			this.namedPositions.put(new ResourceLocation("area_spawn1"), new BlockPos(74, 4, 75));
			this.namedPositions.put(new ResourceLocation("area_spawn2"), new BlockPos(46, 4, 73));
			this.namedPositions.put(new ResourceLocation("area_spawn3"), new BlockPos(48, 4, 75));
			this.namedPositions.put(new ResourceLocation("area_spawn4"), new BlockPos(50, 4, 77));

			this.namedPositions.put(new ResourceLocation("area_spawn5"), new BlockPos(23, 5, 55));
			this.namedPositions.put(new ResourceLocation("area_spawn6"), new BlockPos(24, 5, 56));
			this.namedPositions.put(new ResourceLocation("area_spawn7"), new BlockPos(24, 5, 55));
			this.namedPositions.put(new ResourceLocation("area_spawn8"), new BlockPos(23, 4, 25));
			this.namedPositions.put(new ResourceLocation("area_spawn9"), new BlockPos(24, 4, 25));
			this.namedPositions.put(new ResourceLocation("area_spawn10"), new BlockPos(23, 5, 55));
			this.namedPositions.put(new ResourceLocation("area_spawn11"), new BlockPos(23, 4, 25));

			this.namedPositions.put(new ResourceLocation("area_spawn12"), new BlockPos(73, 4, 27));

			this.namedPositions.put(new ResourceLocation("area_boss1"), new BlockPos(71, 4, 25));
		}

		@Override
		protected BlockPos getPlayerSpawnPosition() {
			return new BlockPos(88, 14, 16);
		}

		@Override
		protected BlockPos getMonsterSpawnPosition() {
			return new BlockPos(54, 4, 40);
		}
	}

	private AreaSandy() {
		super(AreaSandy.schematicLocation, ClipboardFormats.EXTENDED_FORGE_SCHEMATIC);
	}

	@Override
	public String getUnlocalizedName() {
		return ResourceInterface.area_sandy_name;
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

