package mhfc.net.common.quests.factory;

import static mhfc.net.common.util.MHFCJsonUtils.*;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;
import mhfc.net.common.quests.api.*;
import net.minecraft.util.JsonUtils;

public class DefaultQuestFactory implements IQuestFactory {

	@Override
	public GeneralQuest buildQuest(QuestDescription qd) {
		QuestGoal goal = QuestFactory.constructGoal(qd.getGoalDescription());
		if (goal == null)
			return null;
		return new GeneralQuest(goal, qd.getMaxPartySize(), qd.getReward(), qd
			.getFee(), qd.getAreaID(), qd);
	}

	@Override
	public QuestDescription buildQuestDescription(JsonElement json,
		JsonDeserializationContext context) {
		JsonObject jsonAsObject = JsonUtils.getJsonElementAsJsonObject(json,
			"quest");
		if (!jsonAsObject.has("name")) {
			throw new JsonParseException("Every Quest needs a name");
		} else if (!jsonAsObject.has("goal")) {
			throw new JsonParseException("Every Quest needs a goal");
		} else if (!jsonAsObject.has("reward")) {
			throw new JsonParseException("Every Quest needs a reward");
		} else if (!jsonAsObject.has("fee")) {
			throw new JsonParseException("Every Quest needs a fee");
		} else if (!jsonAsObject.has("areaID")) {
			throw new JsonParseException("Every Quest needs an area");
		} else {
			String goal = null;
			GoalDescription goalDesc = null;
			if (jsonAsObject.get("goal").isJsonPrimitive()) {
				goal = JsonUtils.getJsonElementStringValue(jsonAsObject,
					"goal");
			} else {
				goalDesc = context.deserialize(jsonAsObject.get("goal"),
					GoalDescription.class);
			}
			String name = JsonUtils.getJsonObjectStringFieldValue(jsonAsObject,
				"name");
			int timeLimitInS = getJsonObjectIntegerFieldValueOrDefault(
				jsonAsObject, "timeLimit", 50 * 60);
			String description = getJsonObjectStringFieldValueOrDefault(
				jsonAsObject, "description",
				"A new monster threatens the town so go out and kill it soon.");
			String client = getJsonObjectStringFieldValueOrDefault(jsonAsObject,
				"client", "Hunter Guild");

			String aims = getJsonObjectStringFieldValueOrDefault(jsonAsObject,
				"fails", "Kill all big monsters!");
			String fails = getJsonObjectStringFieldValueOrDefault(jsonAsObject,
				"fails", "Died three times or time has run out!");
			String areaId = JsonUtils.getJsonObjectStringFieldValue(
				jsonAsObject, "areaID");

			String typeString = getJsonObjectStringFieldValueOrDefault(
				jsonAsObject, "type", "hunting");
			QuestType type = mhfc.net.common.quests.QuestVisualInformation.QuestType.Hunting;
			switch (typeString) {
				default :
					System.out.println(
						"[MHFC] Type string was not recognized, allowed are hunting, epichunting, gathering and killing\n Falling back to hunting.");
				case "hunting" :
					type = QuestType.Hunting;
					break;
				case "epichunting" :
					type = QuestType.EpicHunting;
					break;
				case "gathering" :
					type = QuestType.Gathering;
					break;
				case "killing" :
					type = QuestType.Killing;
					break;
			}
			int reward = JsonUtils.getJsonObjectIntegerFieldValue(jsonAsObject,
				"reward");
			int fee = JsonUtils.getJsonObjectIntegerFieldValue(jsonAsObject,
				"fee");
			int maxPartySize = getJsonObjectIntegerFieldValueOrDefault(
				jsonAsObject, "maxPartySize", 4);
			String rewardAsString = getJsonObjectStringFieldValueOrDefault(
				jsonAsObject, "rewardAsString", Integer.toString(reward) + "z");
			String feeAsString = getJsonObjectStringFieldValueOrDefault(
				jsonAsObject, "feeAsString", Integer.toString(fee) + "z");
			String timeLimitAsString = getJsonObjectStringFieldValueOrDefault(
				jsonAsObject, "timeLimitAsString", Integer.toString(timeLimitInS
					/ 60) + " min" + ((timeLimitInS % 60 == 0)
						? ""
						: " " + Integer.toString(timeLimitInS % 60) + " s"));
			String maxPartySizeAsString = getJsonObjectStringFieldValueOrDefault(
				jsonAsObject, "maxPartySizeAsString", Integer.toString(
					maxPartySize) + " hunters");
			if (goal != null)
				return new QuestDescription(goal, name, type, reward, fee,
					maxPartySize, areaId, description, client, aims, fails,
					rewardAsString, feeAsString, timeLimitAsString,
					maxPartySizeAsString);
			return new QuestDescription(goalDesc, name, type, reward, fee,
				maxPartySize, areaId, description, client, aims, fails,
				rewardAsString, feeAsString, timeLimitAsString,
				maxPartySizeAsString);
		}
	}

}
