package mhfc.net.common.quests.api;

public interface IVisualInformation {

	String getSerializerType();

	String getName();

	String getDescription();

	String getClient();

	String getAims();

	String getFails();

	String getTimeLimitAsString();

	String getQuestType();

	String getRewardString();

	String getFeeString();

	String getAreaName();

	String getMaxPartySize();
}
