package mhfc.net.common.quests.api;

import mhfc.net.common.quests.world.IQuestAreaSpawnController;

public interface ISpawnInformation {

	void enqueueSpawns(IQuestAreaSpawnController spawnController);

}
