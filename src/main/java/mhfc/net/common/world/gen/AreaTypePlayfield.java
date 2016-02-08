package mhfc.net.common.world.gen;

import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.quests.world.SpawnControllerAdapter;
import mhfc.net.common.world.area.AreaAdapter;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.area.IExtendedConfiguration;
import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class AreaTypePlayfield implements IAreaType {
	private static class AreaPlayfield extends AreaAdapter {
		private class PlayfieldSpawnController extends SpawnControllerAdapter {
			public PlayfieldSpawnController() {
				super(AreaPlayfield.this);
			}

			@Override
			protected void enqueDefaultSpawns() {
				return;
			}

			@Override
			protected SpawnInformation constructDefaultSpawnInformation(Spawnable entity) {
				int spawnX = config.getChunkSizeX() * 8;
				int spawnZ = config.getChunkSizeZ() * 8;
				int height = world
						.getChunkFromChunkCoords(
								getChunkPosition().posX + spawnX / 16,
								getChunkPosition().posY + spawnZ / 16)
						.getHeightValue(spawnX % 16, spawnZ % 16);
				return new SpawnInformation(entity, 6, height, 6);
			}
		}

		private IQuestAreaSpawnController spawnController;

		public AreaPlayfield(World world) {
			super(world);
			this.spawnController = null;
		}

		public AreaPlayfield(World world, AreaConfiguration config) {
			super(world, config);
			this.spawnController = new PlayfieldSpawnController();
		}

		@Override
		public IQuestAreaSpawnController getSpawnController() {
			return spawnController;
		}

		@Override
		public void teleportToSpawn(EntityPlayer player) {
			CornerPosition chunkPos = getChunkPosition();
			player.posX = chunkPos.posX * 16 + 8;
			player.posZ = chunkPos.posY * 16 + 8;
			player.posY = world.getChunkFromChunkCoords(chunkPos.posX, chunkPos.posY).getHeightValue(8, 8);
		}

		@Override
		public String getUnlocalizedDisplayName() {
			return "area.arena.name";
		}

		@Override
		public void loadFromConfig(AreaConfiguration config) {
			super.loadFromConfig(config);
			this.spawnController = new PlayfieldSpawnController();
		}

	}

	public static final AreaTypePlayfield PLAYFIELD_TYPE = new AreaTypePlayfield();
	public static final AreaTypePlayfield PLAYFIELD_MEDIUM = new AreaTypePlayfield(4, 4);
	public static final AreaTypePlayfield PLAYFIELD_BIG = new AreaTypePlayfield(8, 8);

	private AreaTypePlayfield() {
		this(1, 1);
	}

	public AreaTypePlayfield(int chunkSizeX, int chunkSizeY) {
		this.chunkSizeX = chunkSizeX;
		this.chunkSizeY = chunkSizeY;
	}

	private int chunkSizeX, chunkSizeY;

	@Override
	public IArea populate(World world, AreaConfiguration configuration) {
		int chunksX = configuration.getChunkSizeX();
		int chunksZ = configuration.getChunkSizeZ();
		for (int i = 0; i < 16 * chunksX; i++) {
			for (int j = 0; j < 16 * chunksZ; j++) {
				int x = configuration.getPosition().posX * 16 + i;
				int z = configuration.getPosition().posY * 16 + j;
				world.setBlock(x, 64, z, MHFCBlockRegistry.mhfcblockdirt);
			}
		}
		return new AreaPlayfield(world, configuration);
	}

	@Override
	public IArea provideForLoading(World world) {
		return new AreaPlayfield(world);
	}

	@Override
	public AreaConfiguration configForNewArea() {
		return new AreaConfiguration(chunkSizeX, chunkSizeY, IExtendedConfiguration.EMPTY);
	}

	@Override
	public IExtendedConfiguration configForLoading() {
		return IExtendedConfiguration.EMPTY;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + chunkSizeX;
		result = prime * result + chunkSizeY;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AreaTypePlayfield other = (AreaTypePlayfield) obj;
		if (chunkSizeX != other.chunkSizeX) {
			return false;
		}
		if (chunkSizeY != other.chunkSizeY) {
			return false;
		}
		return true;
	}
}
