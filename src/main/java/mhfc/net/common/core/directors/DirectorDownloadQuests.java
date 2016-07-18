package mhfc.net.common.core.directors;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mhfc.net.common.core.builders.BuilderJsonToQuests;
import mhfc.net.common.core.data.QuestDescriptionRegistryData;
import mhfc.net.common.core.data.QuestDescriptionRegistryData.IQuestDescriptionDirector;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import net.minecraft.util.JsonUtils;

/**
 * Director for loading quests from a byte buffer
 */
public class DirectorDownloadQuests implements IQuestDescriptionDirector {

	JsonObject questDescriptions, goalDescriptions, groups;

	public DirectorDownloadQuests(InputStream bufferStream) {
		JsonParser parser = new JsonParser();
		InputStreamReader reader = new InputStreamReader(bufferStream);
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
	public void construct(QuestDescriptionRegistryData data) {
		BuilderJsonToQuests builder = new BuilderJsonToQuests(data);
		builder.generateQuests(questDescriptions);
		builder.generateGoals(goalDescriptions);
		builder.generateGroupMapping(groups);
	}
}
