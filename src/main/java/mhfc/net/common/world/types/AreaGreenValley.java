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

public class AreaGreenValley extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation(
			"mhfc:schematics/greenvalley2.schematic");
	public static final AreaGreenValley INSTANCE = new AreaGreenValley();

	private static class Area extends EmptyArea {
		public Area(World world, AreaConfiguration config) {
			super(world, config);
			namedPositions.put(new ResourceLocation("area_spawn1"), new BlockPos(23, 13, 79));
			namedPositions.put(new ResourceLocation("area_spawn2"), new BlockPos(29, 13, 83));
			namedPositions.put(new ResourceLocation("area_spawn3"), new BlockPos(49, 11, 85));
			namedPositions.put(new ResourceLocation("area_spawn4"), new BlockPos(51, 11, 86));
			namedPositions.put(new ResourceLocation("area_spawn5"), new BlockPos(47, 11, 84));
			namedPositions.put(new ResourceLocation("area_spawn6"), new BlockPos(88, 4, 26));
			namedPositions.put(new ResourceLocation("area_spawn7"), new BlockPos(89, 4, 26));
			namedPositions.put(new ResourceLocation("area_spawn8"), new BlockPos(87, 4, 24));
			namedPositions.put(new ResourceLocation("cave_spawn_boss"), new BlockPos(63, 4, 66));
			namedPositions.put(new ResourceLocation("boss_spawn1"), new BlockPos(51, 20, 42));
			namedPositions.put(new ResourceLocation("boss_spawn2"), new BlockPos(87, 4, 26));
		}

		@Override
		protected BlockPos getPlayerSpawnPosition() {

			//real Loc Default 114 3 101
			//new Loc Default 35 10  31 player starting position in real xyz = (19,9,15)
			/**
			 * If you are having trouble. Calculate the current Block:xyz in f3 and get the xyz of the current spawn
			 * that is given from your random input.
			 * 
			 * Get the difference (larger - smaller ) value
			 * 
			 * on that the difference will be use to add if your random input is smaller otherwise subtract.
			 * 
			 */
			return new BlockPos(19, 10, 15);
		}


		@Override
		protected BlockPos getMonsterSpawnPosition() {
			return new BlockPos(54, -1, 40);
		}
	}


	private AreaGreenValley() {
		super(AreaGreenValley.schematicLocation, ClipboardFormats.EXTENDED_FORGE_SCHEMATIC);
	}

	@Override
	public String getUnlocalizedName() {
		return ResourceInterface.area_greenvalley_name;
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
