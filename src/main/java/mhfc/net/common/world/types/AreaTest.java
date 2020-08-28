package mhfc.net.common.world.types;

import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.EmptyArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IExtendedConfiguration;
import mhfc.net.common.worldedit.ClipboardFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AreaTest extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation(
			"mhfc:schematics/testworld2.schematic");
	public static final AreaTest INSTANCE = new AreaTest();

	static {
		INSTANCE.setRegistryName("testworld2");
	}

	private static class Area extends EmptyArea {
		public Area(World world, AreaConfiguration config) {
			super(world, config);
		}

		@Override
		protected BlockPos getPlayerSpawnPosition() {

			// real Loc Default 114 3 101
			// new Loc Default 35 10 31 player starting position in real xyz =
			// (19,9,15)
			/**
			 * If you are having trouble. Calculate the current Block:xyz in f3
			 * and get the xyz of the current spawn that is given from your
			 * random input.
			 * 
			 * Get the difference (larger - smaller ) value
			 * 
			 * on that the difference will be use to add if your random input is
			 * smaller otherwise subtract.
			 * 
			 */
			return new BlockPos(19, 10, 15);
		}

		@Override
		protected BlockPos getMonsterSpawnPosition() {
			return new BlockPos(54, -1, 40);
		}
	}

	private AreaTest() {
		super(AreaTest.schematicLocation,
				ClipboardFormats.EXTENDED_FORGE_SCHEMATIC);
	}

	@Override
	public String getUnlocalizedName() {
		return "areatest";
	}

	@Override
	public IArea provideForLoading(World world,
			AreaConfiguration configuration) {
		return new Area(world, configuration);
	}

	@Override
	public IExtendedConfiguration configForLoading() {
		return IExtendedConfiguration.EMPTY;
	}

}
