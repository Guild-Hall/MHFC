package mhfc.net.common.quests.factory;

import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.*;
import static mhfc.net.common.util.MHFCJsonUtils.*;

import com.google.gson.*;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;
import mhfc.net.common.quests.api.*;
import mhfc.net.common.quests.descriptions.DefaultQuestDescription;
import net.minecraft.util.JsonUtils;

public class DefaultQuestFactory implements IQuestFactory {

	@Override
	public GeneralQuest buildQuest(QuestDescription qd) {
		QuestGoal goal = QuestFactory.constructGoal(qd.getGoalReference()
			.getReferredDescription());
		if (goal == null)
			return null;
		return new GeneralQuest(goal, qd.getMaxPartySize(), qd.getReward(), qd
			.getFee(), qd.getAreaID(), qd);
	}

	@Override
	public DefaultQuestDescription buildQuestDescription(JsonElement json,
		JsonDeserializationContext context) {
		JsonObject jsonAsObject = JsonUtils.getJsonElementAsJsonObject(json,
			"quest");
		if (!jsonAsObject.has(KEY_NAME)) {
			throw new JsonParseException("Every Quest needs a name");
		} else if (!jsonAsObject.has(KEY_GOAL)) {
			throw new JsonParseException("Every Quest needs a goal");
		} else if (!jsonAsObject.has(KEY_REWARD)) {
			throw new JsonParseException("Every Quest needs a reward");
		} else if (!jsonAsObject.has(KEY_FEE)) {
			throw new JsonParseException("Every Quest needs a fee");
		} else if (!jsonAsObject.has(KEY_AREA_ID)) {
			throw new JsonParseException("Every Quest needs an area");
		} else {
			GoalReference goal = context.deserialize(jsonAsObject.get(KEY_GOAL),
				GoalReference.class);
			String name = JsonUtils.getJsonObjectStringFieldValue(jsonAsObject,
				KEY_NAME);
			int timeLimitInS = getJsonObjectIntegerFieldValueOrDefault(
				jsonAsObject, KEY_TIME_LIMIT, 50 * 60);
			String description = getJsonObjectStringFieldValueOrDefault(
				jsonAsObject, KEY_DESCRIPTION,
				);
			String client = getJsonObjectStringFieldValueOrDefault(jsonAsObject,
				KEY_CLIENT, );

			String aims = getJsonObjectStringFieldValueOrDefault(jsonAsObject,
				KEY_AIMS, );
			String fails = getJsonObjectStringFieldValueOrDefault(jsonAsObject,
				KEY_FAILS, );
			String areaId = JsonUtils.getJsonObjectStringFieldValue(
				jsonAsObject, KEY_AREA_ID);

			String typeString = getJsonObjectStringFieldValueOrDefault(
				jsonAsObject, KEY_TYPE, "hunting");
			QuestType type = QuestVisualInformation.QuestType.Hunting;
			switch (typeString) {
				case MHFCQuestBuildRegistry.QUEST_TYPE_HUNTING :
					type = QuestType.Hunting;
					break;
				case MHFCQuestBuildRegistry.QUEST_TYPE_EPIC_HUNTING :
					type = QuestType.EpicHunting;
					break;
				case MHFCQuestBuildRegistry.QUEST_TYPE_GATHERING :
					type = QuestType.Gathering;
					break;
				case MHFCQuestBuildRegistry.QUEST_TYPE_KILLING :
					type = QuestType.Killing;
					break;
				default :
					System.out.println("[MHFC] Type " + typeString
						+ " was not recognized, for allowed keys see documentation of MHFCQuestBuildRegistry. Falling back to hunting.");
					type = QuestType.Hunting;
			}
			int reward = JsonUtils.getJsonObjectIntegerFieldValue(jsonAsObject,
				KEY_REWARD);
			int fee = JsonUtils.getJsonObjectIntegerFieldValue(jsonAsObject,
				KEY_FEE);
			int maxPartySize = getJsonObjectIntegerFieldValueOrDefault(
				jsonAsObject, KEY_MAX_PARTY_SIZE, 4);
			String rewardAsString = getJsonObjectStringFieldValueOrDefault(
				jsonAsObject, KEY_REWARD_AS_STRING, Integer.toString(reward)
					+ "z");
			String feeAsString = getJsonObjectStringFieldValueOrDefault(
				jsonAsObject, KEY_FEE_AS_STRING, Integer.toString(fee) + "z");
			String timeLimitAsString = getJsonObjectStringFieldValueOrDefault(
				jsonAsObject, KEY_TIME_LIMIT_AS_STRING, Integer.toString(
					timeLimitInS / 60) + " min" + ((timeLimitInS % 60 == 0)
						? ""
						: " " + Integer.toString(timeLimitInS % 60) + " s"));
			String maxPartySizeAsString = getJsonObjectStringFieldValueOrDefault(
				jsonAsObject, KEY_MAX_PARTY_SIZE_AS_STRING, Integer.toString(
					maxPartySize) + " hunters");
			return new DefaultQuestDescription(goal, name, type, reward, fee,
				maxPartySize, areaId, description, client, aims, fails,
				rewardAsString, feeAsString, timeLimitAsString,
				maxPartySizeAsString);
		}
	}

	@Override
	public JsonObject serialize(QuestDescription description,
		JsonSerializationContext context) {
		DefaultQuestDescription questDesc = (DefaultQuestDescription) description;
		IVisualInformation visual = questDesc.getVisualInformation();
		JsonObject holder = new JsonObject();
		holder.addProperty(KEY_MAX_PARTY_SIZE_AS_STRING, visual
			.getMaxPartySize());
		holder.addProperty(KEY_TIME_LIMIT_AS_STRING, visual
			.getTimeLimitAsString());
		holder.addProperty(KEY_FEE_AS_STRING, visual.getFeeString());
		holder.addProperty(KEY_REWARD_AS_STRING, visual.getRewardString());
		holder.addProperty(KEY_MAX_PARTY_SIZE, questDesc.getMaxPartySize());
		holder.addProperty(KEY_TYPE, visual.getType().getAsString());
		holder.addProperty(KEY_FAILS, visual.getFails());
		holder.addProperty(KEY_AIMS, visual.getAims());
		holder.addProperty(KEY_CLIENT, visual.getClient());
		holder.addProperty(KEY_DESCRIPTION, visual.getDescription());
		// holder.addProperty(KEY_TIME_LIMIT, questDesc.get);
		holder.addProperty(KEY_AREA_ID, questDesc.getAreaID());
		holder.addProperty(KEY_FEE, questDesc.getFee());
		holder.addProperty(KEY_REWARD, questDesc.getReward());
		JsonElement jsonGoalReference = context.serialize(questDesc
			.getGoalReference(), GoalReference.class);
		holder.add(KEY_GOAL, jsonGoalReference);
		holder.addProperty(KEY_NAME, visual.getName());
		return holder;
	}

}
