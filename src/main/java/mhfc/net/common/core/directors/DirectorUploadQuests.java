package mhfc.net.common.core.directors;

import java.io.IOException;
import java.io.StringWriter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import mhfc.net.common.core.builders.BuilderJsonToQuests;
import mhfc.net.common.core.data.QuestDescriptionRegistry;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;

public class DirectorUploadQuests {

	public static String construct(QuestDescriptionRegistry data) throws IOException {
		StringWriter output = new StringWriter();

		JsonWriter jsonWrite = new JsonWriter(output);
		jsonWrite.setIndent("");
		JsonObject holderObject = new JsonObject();
		BuilderJsonToQuests builder = new BuilderJsonToQuests(data);

		JsonElement goalDescriptions = builder.retrieveGoalsAsJson();
		JsonElement questDescriptions = builder.retrieveQuestsAsJson();
		JsonElement groups = builder.retrieveGroupsAsJson();
		holderObject.add(MHFCQuestBuildRegistry.KEY_GOAL_DESCRIPTION, goalDescriptions);
		holderObject.add(MHFCQuestBuildRegistry.KEY_QUEST_DESCRIPTION, questDescriptions);
		holderObject.add(MHFCQuestBuildRegistry.KEY_GROUPS, groups);
		BuilderJsonToQuests.gsonInstance.toJson(holderObject, jsonWrite);
		jsonWrite.flush();

		return output.toString();
	}

}
