package mhfc.net.common.core.builders;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.data.QuestDescriptionRegistry;
import mhfc.net.common.core.data.QuestDescriptionRegistry.QuestGroupData;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.network.serialization.GoalSerializer;
import mhfc.net.common.network.serialization.QuestSerializer;
import mhfc.net.common.quests.api.GoalDefinition;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.GoalReference.GoalRefSerializer;
import mhfc.net.common.quests.api.QuestDefinition;

public class BuilderJsonToQuests {

	public static final ParameterizedType typeOfMapGoal = new ParameterizedType() {
		@Override
		public Type[] getActualTypeArguments() {
			return new Type[] { String.class, GoalDefinition.class };
		}

		@Override
		public Type getRawType() {
			return Map.class;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}
	};
	public static final ParameterizedType typeOfMapQuest = new ParameterizedType() {
		@Override
		public Type[] getActualTypeArguments() {
			return new Type[] { String.class, QuestDefinition.class };
		}

		@Override
		public Type getRawType() {
			return Map.class;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}
	};

	public static final ParameterizedType typeOfGroupMap = new ParameterizedType() {
		@Override
		public Type[] getActualTypeArguments() {
			return new Type[] { String.class, new ParameterizedType() {
				@Override
				public Type getRawType() {
					return List.class;
				}

				@Override
				public Type getOwnerType() {
					return null;
				}

				@Override
				public Type[] getActualTypeArguments() {
					return new Type[] { String.class };
				}
			} };
		}

		@Override
		public Type getRawType() {
			return Map.class;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}
	};

	public static final ParameterizedType typeOfGroupList = new ParameterizedType() {
		@Override
		public Type[] getActualTypeArguments() {
			return new Type[] { String.class };
		}

		@Override
		public Type getRawType() {
			return List.class;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}
	};

	public static final class GroupMappingType {
		@SerializedName(MHFCQuestBuildRegistry.KEY_ORDERED_GROUPS)
		public List<String> orderedGroups = new ArrayList<>();
		@SerializedName(MHFCQuestBuildRegistry.KEY_GROUP_MAPPING)
		public Map<String, List<String>> mapping = new HashMap<>();
	}

	public final static Gson gsonInstance = new GsonBuilder()
			.registerTypeAdapter(GoalDefinition.class, new GoalSerializer())
			.registerTypeAdapter(QuestDefinition.class, new QuestSerializer())
			.registerTypeAdapter(GoalReference.class, new GoalRefSerializer()).serializeNulls().create();

	private QuestDescriptionRegistry dataObject;

	public BuilderJsonToQuests(QuestDescriptionRegistry dataObject) {
		this.dataObject = dataObject;
	}

	public void acceptGoalsFrom(BufferedReader reader) throws IOException {
		acceptGoalsFrom(new JsonReader(reader));
	}

	public void acceptGoalsFrom(JsonReader jsonReader) {
		@SuppressWarnings("unchecked")
		Map<String, GoalDefinition> map = (Map<String, GoalDefinition>) gsonInstance
				.fromJson(jsonReader, typeOfMapGoal);
		acceptGoals(map);
	}

	public void acceptGoals(JsonObject jsonInstance) {
		@SuppressWarnings("unchecked")
		Map<String, GoalDefinition> map = (Map<String, GoalDefinition>) gsonInstance
				.fromJson(jsonInstance, typeOfMapGoal);
		acceptGoals(map);
	}

	private void acceptGoals(Map<String, GoalDefinition> map) {
		if (map == null) {
			return;
		}
		dataObject.fillGoalDescriptions(map);
	}

	public void acceptGroupMappingFrom(BufferedReader reader) {
		JsonReader jsonReader = new JsonReader(reader);
		jsonReader.setLenient(true);
		acceptGroupMappingFrom(jsonReader);
	}

	public void acceptGroupMappingFrom(JsonReader reader) {
		GroupMappingType groupMapping = gsonInstance.fromJson(reader, GroupMappingType.class);
		acceptGroupMapping(groupMapping);
	}

	public void acceptGroupMapping(JsonObject jsonObject) {
		GroupMappingType groupMapping = gsonInstance.fromJson(jsonObject, GroupMappingType.class);
		acceptGroupMapping(groupMapping);
	}

	private void acceptGroupMapping(GroupMappingType groupMapping) {
		if (groupMapping == null) {
			return;
		}
		List<String> orderedGroups = groupMapping.orderedGroups;
		Map<String, List<String>> map = groupMapping.mapping;
		if (!orderedGroups.containsAll(map.keySet())) {
			MHFCMain.logger().warn(
					"Detected quest groups which were not included in the ordering. These will appear last in an undefined order. Maybe you are missing the attribute "
							+ MHFCQuestBuildRegistry.KEY_ORDERED_GROUPS);
		}
		if (!map.keySet().containsAll(orderedGroups)) {
			MHFCMain.logger().warn(
					"Detected ordering for quest groups without quests. These will appear empty. Maybe you are missing the attribute "
							+ MHFCQuestBuildRegistry.KEY_GROUP_MAPPING);
		}
		QuestGroupData groupData = new QuestGroupData();
		for (String key : map.keySet()) {
			groupData.addQuestsToGroup(key, map.get(key));
		}
		groupData.orderGroups(orderedGroups);

		dataObject.addGroups(groupData);
	}

	public void acceptQuestsFrom(BufferedReader reader) throws IOException {
		acceptQuestsFrom(new JsonReader(reader));
	}

	public void acceptQuestsFrom(JsonReader reader) {
		Map<String, QuestDefinition> map = gsonInstance.fromJson(reader, typeOfMapQuest);
		acceptQuests(map);
	}

	public void acceptQuests(JsonObject jsonObject) {
		Map<String, QuestDefinition> map = gsonInstance.fromJson(jsonObject, typeOfMapQuest);
		acceptQuests(map);
	}

	private void acceptQuests(Map<String, QuestDefinition> map) {
		if (map == null) {
			return;
		}
		if (map.containsKey("")) {
			throw new java.util.InputMismatchException("[MHFC] Quest identifier can not be an empty string");
		}
		dataObject.fillQuestDescriptions(map);
	}

	public JsonElement retrieveGoalsAsJson() {
		return gsonInstance.toJsonTree(dataObject.getFullGoalDescriptionMap(), typeOfMapGoal);
	}

	public JsonElement retrieveQuestsAsJson() {
		return gsonInstance.toJsonTree(dataObject.getFullQuestDescriptionMap(), typeOfMapQuest);
	}

	public JsonElement retrieveGroupsAsJson() {
		JsonObject holder = new JsonObject();
		JsonElement groupsInOrder = gsonInstance.toJsonTree(dataObject.getGroupsInOrder(), typeOfGroupList);
		JsonElement groupMap = gsonInstance.toJsonTree(dataObject.getFullGroupMap(), typeOfGroupMap);
		holder.add(MHFCQuestBuildRegistry.KEY_ORDERED_GROUPS, groupsInOrder);
		holder.add(MHFCQuestBuildRegistry.KEY_GROUP_MAPPING, groupMap);
		return holder;
	}
}
