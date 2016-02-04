package mhfc.net.common.quests.world;

import java.io.IOException;

import mhfc.net.common.world.area.AreaAdapter;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.controller.CornerPosition;
import mhfc.net.common.world.gen.AreaTypeSchematic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TestAreaType extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation("mhfc:schematics/Test.schematic");

	private static class Area extends AreaAdapter {
		private class SpawnController extends SpawnControllerAdapter {
			public SpawnController() {
				super(Area.this);
			}

			@Override
			protected void enqueDefaultSpawns() {}

			@Override
			protected SpawnInformation constructDefaultSpawnInformation(Entity entity) {
				return new SpawnInformation(entity, 5, 100, 5);
			}
		}

		IQuestAreaSpawnController spawnController;

		public Area(World world) {
			super(world);
		}

		public Area(World world, CornerPosition pos, AreaConfiguration config) {
			super(world, pos, config);
			spawnController = new SpawnController();
		}

		@Override
		public void loadFromConfig(CornerPosition pos, AreaConfiguration config) {
			super.loadFromConfig(pos, config);
			spawnController = new SpawnController();
		}

		@Override
		public IQuestAreaSpawnController getSpawnController() {
			if (!initialized)
				throw new IllegalStateException("Spawn controller requested before initialization");
			return spawnController;
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
