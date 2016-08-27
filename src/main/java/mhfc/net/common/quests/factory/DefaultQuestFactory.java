package mhfc.net.common.quests.factory;

import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_AREA_ID;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_FEE;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_FLAIR;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_GOAL;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_MAX_PARTY_SIZE;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_QUEST_TYPE;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_REWARD;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_VISUAL;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import mhfc.net.MHFCMain;
import mhfc.net.client.quests.DefaultQuestVisualDefinition;
import mhfc.net.client.quests.DefaultQuestVisualDefinition.QuestVisualInformationFactory;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.IQuestDefinitionFactory;
import mhfc.net.common.quests.api.QuestDefinition;
import mhfc.net.common.quests.descriptions.DefaultQuestDescription;
import mhfc.net.common.quests.descriptions.DefaultQuestDescription.QuestType;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.util.MHFCJsonUtils;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.util.JsonUtils;

public class DefaultQuestFactory implements IQuestDefinitionFactory {

	@Override
	public DefaultQuestDescription buildQuestDescription(JsonElement json, JsonDeserializationContext context) {
		JsonObject jsonAsObject = JsonUtils.getJsonElementAsJsonObject(json, "quest");
		MHFCJsonUtils.requireFields(jsonAsObject, KEY_GOAL, KEY_REWARD, KEY_FEE, KEY_AREA_ID, KEY_QUEST_TYPE);

		GoalReference goal = context.deserialize(jsonAsObject.get(KEY_GOAL), GoalReference.class);
		String areaId = JsonUtils.getJsonObjectStringFieldValue(jsonAsObject, KEY_AREA_ID);
		IAreaType areaType = AreaRegistry.instance.getType(areaId);
		if (areaType == null) {
			throw new NullPointerException("Key " + areaId + " is not a registered area type");
		}
		String typeString = JsonUtils.getJsonObjectStringFieldValue(jsonAsObject, KEY_QUEST_TYPE);
		String flairString = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(jsonAsObject, KEY_FLAIR, "DAYTIME");
		QuestType type = QuestType.Hunting;
		switch (typeString) {
		case MHFCQuestBuildRegistry.QUEST_TYPE_HUNTING:
			type = QuestType.Hunting;
			break;
		case MHFCQuestBuildRegistry.QUEST_TYPE_EPIC_HUNTING:
			type = QuestType.EpicHunting;
			break;
		case MHFCQuestBuildRegistry.QUEST_TYPE_GATHERING:
			type = QuestType.Gathering;
			break;
		case MHFCQuestBuildRegistry.QUEST_TYPE_KILLING:
			type = QuestType.Killing;
			break;
		default:
			MHFCMain.logger().error(
					"[MHFC] Type {} was not recognized, for allowed keys see documentation of MHFCQuestBuildRegistry. Falling back to hunting.",
					typeString);
			type = QuestType.Hunting;
		}
		QuestFlair flair = QuestFlair.DAYTIME;
		try {
			flair = QuestFlair.valueOf(flairString);
		} catch (IllegalArgumentException iae) {
			MHFCMain.logger().error(
					"[MHFC] Flair {} was not recognized, for allowed values see documentation of MHFCQuestBuildRegistry. Falling back to DAYTIME.",
					typeString);
		}
		int reward = JsonUtils.getJsonObjectIntegerFieldValue(jsonAsObject, KEY_REWARD);
		int fee = JsonUtils.getJsonObjectIntegerFieldValue(jsonAsObject, KEY_FEE);
		int maxPartySize = MHFCJsonUtils.getJsonObjectIntegerFieldValueOrDefault(jsonAsObject, KEY_MAX_PARTY_SIZE, 4);

		QuestVisualInformationFactory factory = new QuestVisualInformationFactory()
				.decodingFrom(jsonAsObject.get(KEY_VISUAL), context);
		DefaultQuestDescription description = new DefaultQuestDescription(
				goal,
				type,
				areaType,
				flair,
				reward,
				fee,
				maxPartySize,
				factory::forQuest);
		return description;
	}

	@Override
	public JsonObject serialize(QuestDefinition description, JsonSerializationContext context) {
		DefaultQuestDescription questDesc = DefaultQuestDescription.class.cast(description);
		DefaultQuestVisualDefinition visual = questDesc.getVisualInformation();

		JsonObject holder = new JsonObject();
		holder.addProperty(KEY_MAX_PARTY_SIZE, questDesc.getMaxPartySize());
		holder.addProperty(KEY_QUEST_TYPE, questDesc.getQuestType().getAsString());
		// holder.addProperty(KEY_TIME_LIMIT, questDesc.get);
		String areaName = AreaRegistry.instance.getName(questDesc.getAreaType());
		holder.addProperty(KEY_AREA_ID, areaName == null ? AreaRegistry.NAME_ARENA : areaName);
		holder.addProperty(KEY_FEE, questDesc.getFee());
		holder.addProperty(KEY_REWARD, questDesc.getReward());
		JsonElement jsonGoalReference = context.serialize(questDesc.getGoalReference(), GoalReference.class);
		holder.add(KEY_GOAL, jsonGoalReference);
		QuestVisualInformationFactory visualFactory = new QuestVisualInformationFactory(questDesc);
		JsonElement jsonVisual = visualFactory.serialize(visual, context);
		holder.add(KEY_VISUAL, jsonVisual);

		return holder;
	}

}
