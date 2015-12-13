package mhfc.net.common.quests.factory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.api.IVisualInformationFactory;
import mhfc.net.common.quests.api.QuestDescription;
import mhfc.net.common.util.MHFCJsonUtils;

public class QuestVisualInformationFactory
	implements
		IVisualInformationFactory {

	public static final String KEY_NAME = "name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_CLIENT = "client";
	public static final String KEY_AIMS = "aims";
	public static final String KEY_FAILS = "fails";
	public static final String KEY_AREA_ID = "areaID";
	public static final String KEY_REWARD_AS_STRING = "rewardAsString";
	public static final String KEY_FEE_AS_STRING = "feeAsString";
	public static final String KEY_MAX_PARTY_SIZE_AS_STRING = "maxPartySizeAsString";
	public static final String KEY_TIME_LIMIT_AS_STRING = "timeLimitAsString";
	public static final String KEY_TYPE = "questType";

	private QuestDescription quest;

	public QuestVisualInformationFactory() {
	}

	public QuestVisualInformationFactory(QuestDescription quest) {
		this.quest = quest;
	}

	@Override
	public IVisualInformation buildInformation(JsonElement json,
		JsonDeserializationContext context) {
		JsonObject jsonObject = json.getAsJsonObject();
		String name = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(
			jsonObject, KEY_NAME, getDefaultName());
		String description = MHFCJsonUtils
			.getJsonObjectStringFieldValueOrDefault(jsonObject, KEY_DESCRIPTION,
				getDefaultDescription());
		String client = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(
			jsonObject, KEY_CLIENT, getDefaultClient());
		String aims = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(
			jsonObject, KEY_AIMS, getDefaultAims());
		String fails = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(
			jsonObject, KEY_FAILS, getDefaultFails());
		String areaName = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(
			jsonObject, KEY_AREA_ID, getDefaultAreaID());
		String timeLimit = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(
			jsonObject, KEY_TIME_LIMIT_AS_STRING, getDefaultTimeLimit());
		String reward = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(
			jsonObject, KEY_REWARD_AS_STRING, getDefaultReward());
		String fee = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(
			jsonObject, KEY_FEE_AS_STRING, getDefaultFee());
		String maxPartySize = MHFCJsonUtils
			.getJsonObjectStringFieldValueOrDefault(jsonObject,
				KEY_MAX_PARTY_SIZE_AS_STRING, getDefaultPartySize());
		String type = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(
			jsonObject, KEY_TYPE, getDefaultType());
		return new QuestVisualInformation(name, description, client, aims,
			fails, areaName, timeLimit, reward, fee, maxPartySize, type);
	}

	private String getDefaultName() {
		return "Quest";
	}

	private String getDefaultDescription() {
		return "A new monster threatens the town so go out and kill it soon.";
	}

	private String getDefaultClient() {
		return "Hunter Guild";
	}

	private String getDefaultAims() {
		return "Kill all big monsters!";
	}

	private String getDefaultFails() {
		return "Died three times or time has run out!";
	}

	private String getDefaultAreaID() {
		// FIXME when areas are implemented, this needs a fix
		return quest == null ? "Unknown" : quest.getAreaID();
	}

	private String getDefaultTimeLimit() {
		return "As fast as possible";
	}

	private String getDefaultReward() {
		return quest == null
			? "Gratitude from everybody"
			: quest.getReward() + "z";
	}

	private String getDefaultFee() {
		return quest == null ? "Unknown" : quest.getFee() + "z";
	}

	private String getDefaultPartySize() {
		return quest == null
			? "A few friends"
			: quest.getMaxPartySize() + " hunters";
	}

	private String getDefaultType() {
		return quest == null ? "Unknown" : quest.getType();
	}

	@Override
	public JsonElement serialize(IVisualInformation information,
		JsonSerializationContext context) {
		JsonObject holder = new JsonObject();

	}

}
