package mhfc.net.common.quests.factory;

import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_AREA_ID;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_FEE;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_FLAIR;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_GOAL;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_MAX_PARTY_SIZE;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_QUEST_TYPE;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_REWARD;
import static mhfc.net.common.quests.descriptions.DefaultQuestDescription.KEY_SPAWNS;
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
import mhfc.net.common.quests.api.IQuestDefinition;
import mhfc.net.common.quests.api.IQuestDefinitionFactory;
import mhfc.net.common.quests.api.QuestRewardDelegate;
import mhfc.net.common.quests.api.SpawnInformationDelegate;
import mhfc.net.common.quests.descriptions.DefaultQuestDescription;
import mhfc.net.common.quests.descriptions.DefaultQuestDescription.QuestType;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.util.MHFCJsonUtils;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.util.JsonUtils;

public class DefaultQuestFactory implements IQuestDefinitionFactory {

	@Override
	public DefaultQuestDescription convertTo(JsonElement json, JsonDeserializationContext context) {
		final JsonObject jsonAsObject = JsonUtils.getJsonObject(json, "quest");
		MHFCJsonUtils.requireFields(jsonAsObject, KEY_GOAL, KEY_FEE, KEY_AREA_ID, KEY_QUEST_TYPE);

		final GoalReference goal = context.deserialize(jsonAsObject.get(KEY_GOAL), GoalReference.class);

		final String typeString = JsonUtils.getString(jsonAsObject, KEY_QUEST_TYPE);
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

		final String areaId = JsonUtils.getString(jsonAsObject, KEY_AREA_ID);
		final IAreaType areaType = AreaRegistry.instance.getType(areaId);
		if (areaType == null) {
			throw new NullPointerException("Key " + areaId + " is not a registered area type");
		}

		QuestFlair flair = QuestFlair.DAYTIME;
		final String flairString = MHFCJsonUtils
				.getJsonObjectStringFieldValueOrDefault(jsonAsObject, KEY_FLAIR, QuestFlair.DAYTIME.name());
		try {
			flair = QuestFlair.valueOf(flairString);
		} catch (final IllegalArgumentException iae) {
			MHFCMain.logger().error(
					"[MHFC] Flair {} was not recognized, for allowed values see documentation of MHFCQuestBuildRegistry. Falling back to DAYTIME.",
					flairString);
		}

		final QuestRewardDelegate reward = QuestRewardDelegate.deserialize(jsonAsObject.get(KEY_REWARD), context);
		final int fee = JsonUtils.getInt(jsonAsObject, KEY_FEE);
		final int maxPartySize = MHFCJsonUtils
				.getJsonObjectIntegerFieldValueOrDefault(jsonAsObject, KEY_MAX_PARTY_SIZE, 4);
		final SpawnInformationDelegate spawns = SpawnInformationDelegate
				.deserialize(jsonAsObject.get(KEY_SPAWNS), context);

		final QuestVisualInformationFactory factory = new QuestVisualInformationFactory()
				.decodingFrom(jsonAsObject.get(KEY_VISUAL), context);
		final DefaultQuestDescription description = new DefaultQuestDescription(
				goal,
				type,
				areaType,
				flair,
				reward,
				spawns,
				fee,
				maxPartySize,
				factory::forQuest);
		return description;
	}

	@Override
	public JsonObject convertFrom(IQuestDefinition description, JsonSerializationContext context) {
		final DefaultQuestDescription questDesc = DefaultQuestDescription.class.cast(description);
		final DefaultQuestVisualDefinition visual = questDesc.getVisualInformation();

		final JsonObject holder = new JsonObject();
		final JsonElement jsonGoalReference = context.serialize(questDesc.getGoalReference(), GoalReference.class);
		holder.add(KEY_GOAL, jsonGoalReference);
		holder.addProperty(KEY_QUEST_TYPE, questDesc.getQuestType().getAsString());
		final String areaName = AreaRegistry.instance.getName(questDesc.getAreaType());
		holder.addProperty(KEY_AREA_ID, areaName == null ? AreaRegistry.NAME_ARENA : areaName);
		holder.addProperty(KEY_FLAIR, questDesc.getQuestFlair().name());
		final JsonElement reward = context.serialize(questDesc.getReward(), QuestRewardDelegate.class);
		holder.add(KEY_REWARD, reward);
		holder.addProperty(KEY_FEE, questDesc.getFee());
		holder.addProperty(KEY_MAX_PARTY_SIZE, questDesc.getMaxPartySize());
		final JsonElement spawns = context.serialize(questDesc.getSpawnInformation());
		holder.add(KEY_SPAWNS, spawns);
		final JsonElement jsonVisual = QuestVisualInformationFactory.serialize(visual, context);
		holder.add(KEY_VISUAL, jsonVisual);

		return holder;
	}

}
