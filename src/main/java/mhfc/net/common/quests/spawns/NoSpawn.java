package mhfc.net.common.quests.spawns;

import mhfc.net.common.quests.api.ISpawnInformation;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;

public final class NoSpawn implements ISpawnInformation {
	public static final NoSpawn INSTANCE = new NoSpawn();

	private NoSpawn() {}

	@Override
	public void enqueueSpawns(IQuestAreaSpawnController spawnController) {
		;
	}
}
