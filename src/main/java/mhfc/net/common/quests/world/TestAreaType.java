package mhfc.net.common.quests.world;

import java.io.IOException;

import mhfc.net.common.quests.world.SpawnControllerAdapter.SpawnInformation;
import mhfc.net.common.quests.world.SpawnControllerAdapter.Spawnable;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.EmptyArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.controller.CornerPosition;
import mhfc.net.common.world.gen.AreaTypeSchematic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TestAreaType extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation("mhfc:schematics/Test.schematic");

	private static class Area extends EmptyArea {

		public Area(World world, CornerPosition pos, AreaConfiguration config) {
			super(world, pos, config);
		}

		public Area(World world) {
			super(world);
		}

		@Override
		public void teleportToSpawn(EntityPlayer player) {
			player.posX = chunkPos.posX * 16 + 3;
			player.posY = 8;
			player.posZ = chunkPos.posY * 16 + 3;
		}

		@Override
		public String getUnlocalizedDisplayName() {
			return "area.test.name";
		}

		@Override
		public SpawnInformation constructDefaultSpawnInformation(Spawnable entity) {
			return new SpawnInformation(entity, 10, 9, 10);
		}

	}

	public static TestAreaType INSTANCE;

	static {
		try {
			INSTANCE = new TestAreaType();
		} catch (IOException e) {
			throw new RuntimeException("Could not load test area", e);
		}
	}

	private TestAreaType() throws IOException {
		super(schematicLocation);
	}

	@Override
	public IArea provideForLoading(World world) {
		return new Area(world);
	}

	@Override
	public AreaConfiguration configForLoading() {
		return new AreaConfiguration();
	}

	@Override
	protected IArea onPopulate(World world, CornerPosition lowerLeftCorner, AreaConfiguration configuration) {
		return new Area(world, lowerLeftCorner, configuration);
	}

}
