package mhfc.net.common.world.gen;

import java.util.Objects;

import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.quests.world.SpawnControllerAdapter;
import mhfc.net.common.world.area.*;
import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class AreaTypeArena implements IAreaType {
	private static class AreaArena implements IArea {
		private class ArenaSpawnController extends SpawnControllerAdapter {
			public ArenaSpawnController() {
				super(AreaArena.this);
			}

			@Override
			protected void enqueDefaultSpawns() {
				return;
			}

			@Override
			protected SpawnInformation constructDefaultSpawnInformation(Entity entity) {
				int height = world.getChunkFromChunkCoords(chunkPos.posX, chunkPos.posY).getHeightValue(8, 8);
				return new SpawnInformation(entity, 6, height, 6);
			}
		}

		private final World world;
		private CornerPosition chunkPos;
		private AreaConfiguration config;
		private IWorldView worldView;
		private IQuestAreaSpawnController spawnController;

		public AreaArena(World world) {
			this.world = Objects.requireNonNull(world);
			this.chunkPos = null;
			this.config = null;
			this.worldView = null;
			this.spawnController = null;
		}

		public AreaArena(World world, CornerPosition pos, AreaConfiguration config) {
			this.world = Objects.requireNonNull(world);
			this.chunkPos = Objects.requireNonNull(pos);
			this.config = Objects.requireNonNull(config);
			this.worldView = new WorldViewDisplaced(chunkPos, config, world);
			this.spawnController = new ArenaSpawnController();
		}

		@Override
		public IQuestAreaSpawnController getSpawnController() {
			return spawnController;
		}

		@Override
		public void teleportToSpawn(EntityPlayer player) {
			player.posX = chunkPos.posX * 16 + 8;
			player.posZ = chunkPos.posY * 16 + 8;
			player.posY = world.getChunkFromChunkCoords(chunkPos.posX, chunkPos.posY).getHeightValue(8, 8);
		}

		@Override
		public String getUnlocalizedDisplayName() {
			return "area.arena.name";
		}

		@Override
		public void loadFromConfig(CornerPosition pos, AreaConfiguration config) {
			this.chunkPos = Objects.requireNonNull(pos);
			this.config = Objects.requireNonNull(config);
			this.worldView = new WorldViewDisplaced(chunkPos, config, world);
			this.spawnController = new ArenaSpawnController();
		}

		@Override
		public IWorldView getWorldView() {
			return worldView;
		}
	}

	public static final AreaTypeArena ARENA_TYPE = new AreaTypeArena();

	private AreaTypeArena() {}

	@Override
	public IArea populate(World world, CornerPosition lowerLeftCorner, AreaConfiguration configuration) {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				int x = lowerLeftCorner.posX * 16 + i;
				int z = lowerLeftCorner.posY * 16 + j;
				world.setBlock(x, 64, z, MHFCBlockRegistry.mhfcblockdirt);
			}
		}
		return new AreaArena(world, lowerLeftCorner, configuration);
	}

	@Override
	public IArea provideForLoading(World world) {
		return new AreaArena(world);
	}

	@Override
	public AreaConfiguration configForNewArea() {
		return new AreaConfiguration(1, 1);
	}

	@Override
	public AreaConfiguration configForLoading() {
		return new AreaConfiguration();
	}
}
