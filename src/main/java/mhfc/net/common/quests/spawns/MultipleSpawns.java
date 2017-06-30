package mhfc.net.common.quests.spawns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mhfc.net.common.quests.api.ISpawnInformation;
import mhfc.net.common.quests.api.SpawnInformationDelegate;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;

public class MultipleSpawns implements ISpawnInformation {

	private final List<SpawnInformationDelegate> spawns;

	public MultipleSpawns(List<SpawnInformationDelegate> spawns) {
		this.spawns = new ArrayList<>(spawns);
	}

	@Override
	public void enqueueSpawns(IQuestAreaSpawnController spawnController) {
		spawns.forEach(s -> s.getValue().enqueueSpawns(spawnController));
	}

	public List<SpawnInformationDelegate> getSpawns() {
		return Collections.unmodifiableList(spawns);
	}

}
