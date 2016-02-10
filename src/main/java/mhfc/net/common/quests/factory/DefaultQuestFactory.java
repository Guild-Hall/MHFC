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
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.IQuestFactory;
import mhfc.net.common.quests.api.IVisualInformationFactory;
import mhfc.net.common.quests.api.QuestDescription;
import mhfc.net.common.quests.api.QuestFactory;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.descriptions.DefaultQuestDescription;
import mhfc.net.common.quests.world.GlobalAreaManager;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.util.MHFCJsonUtils;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.util.JsonUtils;

public class DefaultQuestFactory implements IQuestFactory {

	@Override
	public GeneralQuest buildQuest(QuestDescription qd) {
		QuestGoal goal = QuestFactory.constructGoal(qd.getGoalReference().getReferredDescription());
		if (goal == null) {
			return null;
		}
		IAreaType areaType = qd.getAreaType();

		IActiveArea activeArea = GlobalAreaManager.getInstance().getUnusedInstance(areaType, qd.getQuestFlair());
		if (activeArea == null) {
			return null;
		}

		return new GeneralQuest(goal, qd.getMaxPartySize(), qd.getReward(), qd.getFee(), activeArea, qd);
	}

	@Override
	public DefaultQuestDescription buildQuestDescription(JsonElement json, JsonDeserializationContext context) {
		JsonObject jsonAsObject = JsonUtils.getJsonElementAsJsonObject(json, "quest");
		MHFCJsonUtils.requireFields(jsonAsObject, KEY_GOAL, KEY_REWARD, KEY_FEE, KEY_AREA_ID, KEY_QUEST_TYPE);

		GoalReference goal = context.deserialize(jsonAsObject.get(KEY_GOAL), GoalReference.class);
		String areaId = JsonUtils.getJsonObjectStringFieldValue(jsonAsObject, KEY_AREA_ID);
		String typeString = JsonUtils.getJsonObjectStringFieldValue(jsonAsObject, KEY_QUEST_TYPE);
		String flairString = MHFCJsonUtils.getJsonObjectStringFieldValueOrDefault(jsonAsObject, KEY_FLAIR, "DAYTIME");
		QuestDescription.QuestType type = QuestDescription.QuestType.Hunting;
		switch (typeString) {
		case MHFCQuestBuildRegistry.QUEST_TYPE_HUNTING:
			type = QuestDescription.QuestType.Hunting;
			break;
		case MHFCQuestBuildRegistry.QUEST_TYPE_EPIC_HUNTING:
			type = QuestDescription.QuestType.EpicHunting;
			break;
		case MHFCQuestBuildRegistry.QUEST_TYPE_GATHERING:
			type = QuestDescription.QuestType.Gathering;
			break;
		case MHFCQuestBuildRegistry.QUEST_TYPE_KILLING:
			type = QuestDescription.QuestType.Killing;
			break;
		default:
			MHFCMain.logger.error(
					"[MHFC] Type " + typeString
							+ " was not recognized, for allowed keys see documentation of MHFCQuestBuildRegistry. Falling back to hunting.");
			type = QuestDescription.QuestType.Hunting;
		}
		QuestFlair flair = QuestFlair.DAYTIME;
		try {
			flair = QuestFlair.valueOf(flairString);
		} catch (IllegalArgumentException iae) {
			MHFCMain.logger.error(
					"[MHFC] Flair " + typeString
							+ " was not recognized, for allowed values see documentation of MHFCQuestBuildRegistry. Falling back to DAYTIME.");
		}
		int reward = JsonUtils.getJsonObjectIntegerFieldValue(jsonAsObject, KEY_REWARD);
		int fee = JsonUtils.getJsonObjectIntegerFieldValue(jsonAsObject, KEY_FEE);
		int maxPartySize = MHFCJsonUtils.getJsonObjectIntegerFieldValueOrDefault(jsonAsObject, KEY_MAX_PARTY_SIZE, 4);

		DefaultQuestDescription description = new DefaultQuestDescription(
				goal,
				type,
				areaId,
				flair,
				reward,
				fee,
				maxPartySize);
		IVisualInformationFactory defaultFactory = new QuestVisualInformationFactory(description);
		JsonElement visualInformation = jsonAsObject.get(KEY_VISUAL);
		IVisualInformation visual = defaultFactory.buildInformation(visualInformation, context);
		description.setVisualInformation(visual);
		return description;
	}

	@Override
	public JsonObject serialize(QuestDescription description, JsonSerializationContext context) {
		DefaultQuestDescription questDesc = (DefaultQuestDescription) description;
		IVisualInformation visual = questDesc.getVisualInformation();

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
		IVisualInformationFactory visualFactory = QuestFactory
				.getQuestVisualInformationFactory(visual.getSerializerType());
		JsonElement jsonVisual = visualFactory.serialize(visual, context);
		holder.add(KEY_VISUAL, jsonVisual);

		return holder;
	}

}
