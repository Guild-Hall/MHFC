package mhfc.net.common.quests.world;

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

public class ArenaType extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation("mhfc:schematics/Arena.schematic");

	private static class Area extends EmptyArea {
		public Area(World world, AreaConfiguration config) {
			super(world, config);
		}

		public Area(World world) {
			super(world);
		}

		@Override
		public void teleportToSpawn(EntityPlayer player) {
			double posX = getChunkPosition().posX * 16 + 54;
			double posZ = getChunkPosition().posY * 16 + 11;
			double posY = world.getTopSolidOrLiquidBlock((int) posX, (int) posZ) + 1;
			player.setPosition(posX, posY, posZ);
		}

		@Override
		public String getUnlocalizedDisplayName() {
			return "area.arena.name";
		}

		@Override
		public SpawnInformation constructDefaultSpawnInformation(Spawnable entity) {
			return new SpawnInformation(entity, 50, 54.5, 62);
		}

	}

	private ArenaType() throws IOException {
		super(ArenaType.schematicLocation);
	}

	public static ArenaType INSTANCE;

	static {
		try {
			ArenaType.INSTANCE = new ArenaType();
		} catch (IOException e) {
			throw new RuntimeException("Could not load test area", e);
		}
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
