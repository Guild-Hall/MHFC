package mhfc.net.common.quests.world;

import java.util.Optional;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public interface IQuestArea {
	public static final ResourceLocation PLAYER_SPAWN = new ResourceLocation("player_spawn");
	public static final ResourceLocation MONSTER_SPAWN = new ResourceLocation("monster_spawn");

	public IQuestAreaSpawnController getSpawnController();

	/**
	 * Resolves the (local) block position named location. Positions that should always be supported are
	 * {@link #PLAYER_SPAWN} and {@link #MONSTER_SPAWN}
	 *
	 * @param location
	 *            the name of the location to retrieve.
	 * @return an empty optional if no such location is known
	 */
	public Optional<BlockPos> resolveLocation(ResourceLocation location);

	public default BlockPos resolveMonsterSpawn(ResourceLocation locationHint) {
		return resolveLocation(locationHint).orElseGet(() -> resolveLocation(MONSTER_SPAWN).get());
	}

	public default BlockPos resolvePlayerSpawn(ResourceLocation locationHint) {
		return resolveLocation(locationHint).orElseGet(() -> resolveLocation(PLAYER_SPAWN).get());
	}
}
