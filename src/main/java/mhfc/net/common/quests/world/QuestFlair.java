package mhfc.net.common.quests.world;

public enum QuestFlair {
	DAYTIME(1000L),
	NIGHTTIME(16000L),
	RAINING(13000L);

	public final long worldTime;

	private QuestFlair(long time) {
		this.worldTime = time;
	}
}
