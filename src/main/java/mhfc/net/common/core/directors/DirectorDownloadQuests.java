package mhfc.net.common.core.directors;

import java.io.StringReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mhfc.net.common.core.builders.BuilderJsonToQuests;
import mhfc.net.common.core.data.QuestDescriptionRegistry;
import mhfc.net.common.core.data.QuestDescriptionRegistry.IQuestDescriptionDirector;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import net.minecraft.util.JsonUtils;

/**
 * Director for loading quests from a byte buffer
 */
public class DirectorDownloadQuests implements IQuestDescriptionDirector {

	JsonObject questDescriptions, goalDescriptions, groups;

	public DirectorDownloadQuests(String inputJson) {
		JsonParser parser = new JsonParser();
		StringReader reader = new StringReader(inputJson);
		JsonObject jsonRepresentation = JsonUtils
				.getJsonElementAsJsonObject(parser.parse(reader), "quest description data");
		JsonElement jsonQuestDescription = jsonRepresentation.get(MHFCQuestBuildRegistry.KEY_QUEST_DESCRIPTION);
		JsonElement jsonGoalDescription = jsonRepresentation.get(MHFCQuestBuildRegistry.KEY_GOAL_DESCRIPTION);
		JsonElement jsonGroups = jsonRepresentation.get(MHFCQuestBuildRegistry.KEY_GROUPS);
		questDescriptions = JsonUtils
				.getJsonElementAsJsonObject(jsonQuestDescription, MHFCQuestBuildRegistry.KEY_QUEST_DESCRIPTION);
		goalDescriptions = JsonUtils
				.getJsonElementAsJsonObject(jsonGoalDescription, MHFCQuestBuildRegistry.KEY_GOAL_DESCRIPTION);
		groups = JsonUtils.getJsonElementAsJsonObject(jsonGroups, MHFCQuestBuildRegistry.KEY_GROUPS);
	}

	@Override
	public void construct(QuestDescriptionRegistry registry) {
		BuilderJsonToQuests builder = new BuilderJsonToQuests(registry);
		builder.acceptQuests(questDescriptions);
		builder.acceptGoals(goalDescriptions);
		builder.acceptGroupMapping(groups);
	}
}
