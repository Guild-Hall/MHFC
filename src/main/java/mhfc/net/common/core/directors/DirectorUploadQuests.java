package mhfc.net.common.core.directors;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import mhfc.net.common.core.builders.BuilderJsonToQuests;
import mhfc.net.common.core.data.QuestDescriptionRegistryData;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;

public class DirectorUploadQuests {

	private OutputStream outStream;

	public DirectorUploadQuests(OutputStream out) {
		outStream = out;
	}

	public void construct(QuestDescriptionRegistryData data) {
		OutputStreamWriter writer = new OutputStreamWriter(outStream);
		JsonWriter jsonWriter = new JsonWriter(writer);
		jsonWriter.setIndent("");
		JsonObject holderObject = new JsonObject();
		BuilderJsonToQuests builder = new BuilderJsonToQuests(data);

		JsonElement goalDescriptions = builder.getGoalsAsJson();
		JsonElement questDescriptions = builder.getQuestsAsJson();
		JsonElement groups = builder.getGroupsAsJson();
		holderObject.add(MHFCQuestBuildRegistry.KEY_GOAL_DESCRIPTION,
			goalDescriptions);
		holderObject.add(MHFCQuestBuildRegistry.KEY_QUEST_DESCRIPTION,
			questDescriptions);
		holderObject.add(MHFCQuestBuildRegistry.KEY_GROUPS, groups);
		BuilderJsonToQuests.gsonInstance.toJson(holderObject, jsonWriter);
		try {
			jsonWriter.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
