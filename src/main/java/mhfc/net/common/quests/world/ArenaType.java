package mhfc.net.common.quests.world;

import java.io.IOException;

import mhfc.net.common.quests.world.SpawnControllerAdapter.SpawnInformation;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.EmptyArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.controller.CornerPosition;
import mhfc.net.common.world.gen.AreaTypeSchematic;
import net.minecraft.entity.Entity;
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
			player.posX = chunkPos.posX * 16 + 54;
			player.posZ = chunkPos.posY * 16 + 11;
			player.posY = world.getChunkFromBlockCoords((int) player.posX, (int) player.posZ)
					.getHeightValue((int) player.posX % 16, (int) player.posZ % 16) + 1;
		}

		@Override
		public String getUnlocalizedDisplayName() {
			return "area.arena.name";
		}

		@Override
		public SpawnInformation constructDefaultSpawnInformation(Entity entity) {
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
