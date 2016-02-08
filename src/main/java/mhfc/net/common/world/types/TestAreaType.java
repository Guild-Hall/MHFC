package mhfc.net.common.world.types;

import java.io.IOException;

import mhfc.net.common.quests.world.SpawnControllerAdapter.SpawnInformation;
import mhfc.net.common.quests.world.SpawnControllerAdapter.Spawnable;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.EmptyArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IExtendedConfiguration;
import mhfc.net.common.world.gen.AreaTypeSchematic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TestAreaType extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation("mhfc:schematics/Test.schematic");

	private static class Area extends EmptyArea {

		public Area(World world, AreaConfiguration config) {
			super(world, config);
		}

		public Area(World world) {
			super(world);
		}

		@Override
		public void teleportToSpawn(EntityPlayer player) {
			player.posX = getChunkPosition().posX * 16 + 3;
			player.posY = 8;
			player.posZ = getChunkPosition().posY * 16 + 3;
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
			TestAreaType.INSTANCE = new TestAreaType();
		} catch (IOException e) {
			throw new RuntimeException("Could not load test area", e);
		}
	}

	private TestAreaType() throws IOException {
		super(TestAreaType.schematicLocation);
	}

	@Override
	public IArea provideForLoading(World world) {
		return new Area(world);
	}

	@Override
	public IExtendedConfiguration configForLoading() {
		return IExtendedConfiguration.EMPTY;
	}

	@Override
	protected IArea onPopulate(World world, AreaConfiguration configuration) {
		return new Area(world, configuration);
	}

}
