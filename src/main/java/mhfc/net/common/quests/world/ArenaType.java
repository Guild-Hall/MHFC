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

public class ArenaType extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation("mhfc:schematics/Arena.schematic");

	private static class Area extends EmptyArea {
		public Area(World world, CornerPosition pos, AreaConfiguration config) {
			super(world, pos, config);
		}

		public Area(World world) {
			super(world);
		}

		@Override
		public void teleportToSpawn(EntityPlayer player) {
			double posX = chunkPos.posX * 16 + 54;
			double posZ = chunkPos.posY * 16 + 11;
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
		super(schematicLocation);
	}

	public static ArenaType INSTANCE;

	static {
		try {
			INSTANCE = new ArenaType();
		} catch (IOException e) {
			throw new RuntimeException("Could not load test area", e);
		}
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
