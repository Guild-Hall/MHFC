package mhfc.net.common.quests;

public class QuestSpawnController {

	// FIXME Change to real area instead of ID
	private String areaID;

	public QuestSpawnController(String areaID) {
		this.areaID = areaID;
	}

	public void spawn(String entityID) {
		// FIXME spawn a mob
	}

	public String[] getSpawnedEntityIDs() {
		// TODO
		return new String[0];
	}
}