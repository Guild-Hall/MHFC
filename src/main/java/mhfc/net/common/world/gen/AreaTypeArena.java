package mhfc.net.common.world.gen;

import java.util.Objects;

import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class AreaTypeArena implements IAreaType {
	private static class AreaArena implements IArea {
		private final World world;
		private CornerPosition chunkPos;
		private AreaConfiguration config;

		public AreaArena(World world) {
			this.world = Objects.requireNonNull(world);
			this.chunkPos = null;
			this.config = null;
		}

		public AreaArena(World world, CornerPosition pos, AreaConfiguration config) {
			this.world = Objects.requireNonNull(world);
			this.chunkPos = Objects.requireNonNull(pos);
			this.config = Objects.requireNonNull(config);
		}

		@Override
		public IQuestAreaSpawnController getSpawnController() {
			// TODO Auto-generated method stub
			return null;
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
