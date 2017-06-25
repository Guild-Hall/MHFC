package mhfc.net.common.quests.world;

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
	 * @return
	 */
	public BlockPos resolvePosition(ResourceLocation location);
}
