package mhfc.net.common.world.types;

import java.util.List;

import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.RunContext;

import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.quests.world.SpawnControllerAdapter;
import mhfc.net.common.world.area.AreaAdapter;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.area.IExtendedConfiguration;
import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AreaTypePlayfield implements IAreaType {
	private static class AreaPlayfield extends AreaAdapter {
		private class PlayfieldSpawnController extends SpawnControllerAdapter {
			public PlayfieldSpawnController() {
				super(AreaPlayfield.this.worldView);
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

		public AreaPlayfield(World world, AreaConfiguration config) {
			super(world, config);
		}

		@Override
		public void teleportToSpawn(EntityPlayer player) {
			CornerPosition chunkPos = getChunkPosition();
			double posX = 8;
			double posZ = 8;
			double posY = world.getChunkFromChunkCoords(chunkPos.posX, chunkPos.posY).getHeightValue(8, 8);
			worldView.moveEntityTo(player, posX, posY, posZ);
		}

		@Override
		protected IQuestAreaSpawnController initializeSpawnController() {
			PlayfieldSpawnController playfieldSpawnController = new PlayfieldSpawnController();
			playfieldSpawnController.setAreaInstance(this);
			return playfieldSpawnController;
		}

	}

	public static final AreaTypePlayfield PLAYFIELD_TYPE = new AreaTypePlayfield();
	public static final AreaTypePlayfield PLAYFIELD_MEDIUM = new AreaTypePlayfield(4, 4);
	public static final AreaTypePlayfield PLAYFIELD_BIG = new AreaTypePlayfield(8, 8);

	private AreaTypePlayfield() {
		this(1, 1);
	}

	@Override
	public String getUnlocalizedName() {
		return ResourceInterface.area_playfield_name;
	}

	public AreaTypePlayfield(int chunkSizeX, int chunkSizeY) {
		this.chunkSizeX = chunkSizeX;
		this.chunkSizeY = chunkSizeY;
	}

	private int chunkSizeX, chunkSizeY;

	@Override
	public Operation populate(World world, AreaConfiguration configuration) {
		int chunksX = configuration.getChunkSizeX();
		int chunksZ = configuration.getChunkSizeZ();
		return new Operation() {
			@Override
			public Operation resume(RunContext run) {
				for (int i = 0; i < 16 * chunksX; i++) {
					for (int j = 0; j < 16 * chunksZ; j++) {
						int x = configuration.getPosition().posX * 16 + i;
						int z = configuration.getPosition().posY * 16 + j;
						world.setBlockState(
								new BlockPos(x, 64, z),
								MHFCBlockRegistry.getRegistry().mhfcblockdirt.getDefaultState());
					}
				}
				return null;
			}


			@Override
			public void cancel() {}


			@Override
			public void addStatusMessages(List<String> messages) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public IArea provideForLoading(World world, AreaConfiguration configuration) {
		return new AreaPlayfield(world, configuration);
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
