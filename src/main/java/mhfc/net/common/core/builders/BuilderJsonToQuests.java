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
import mhfc.net.common.core.data.QuestDescriptionRegistryData;
import mhfc.net.common.core.data.QuestDescriptionRegistryData.QuestGroupData;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.network.serialization.GoalSerializer;
import mhfc.net.common.network.serialization.QuestSerializer;
import mhfc.net.common.network.serialization.QuestVisualSerializer;
import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.quests.api.GoalDescription;
import mhfc.net.common.quests.api.GoalReference;
import mhfc.net.common.quests.api.GoalReference.GoalRefSerializer;
import mhfc.net.common.quests.api.QuestDescription;

public class BuilderJsonToQuests {

	public static final ParameterizedType typeOfMapGoal = new ParameterizedType() {
		@Override
		public Type[] getActualTypeArguments() {
			return new Type[]{String.class, GoalDescription.class};
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
			return new Type[]{String.class, QuestDescription.class};
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
			return new Type[]{String.class, new ParameterizedType() {
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
							return new Type[]{String.class};
						}
					}};
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
			return new Type[]{String.class};
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

	public final static Gson gsonInstance;

	static {
		GsonBuilder builder = (new GsonBuilder());
		builder.registerTypeAdapter(GoalDescription.class,
			new GoalSerializer());
		builder.registerTypeAdapter(QuestDescription.class,
			new QuestSerializer());
		builder.registerTypeAdapter(GoalReference.class,
			new GoalRefSerializer());
		builder.registerTypeAdapter(IVisualInformation.class,
			new QuestVisualSerializer());
		builder.serializeNulls();
		gsonInstance = builder.create();
	}

	private QuestDescriptionRegistryData dataObject;

	public BuilderJsonToQuests(QuestDescriptionRegistryData dataObject) {
		this.dataObject = dataObject;
	}

	public void generateGoals(BufferedReader reader) throws IOException {
		try (JsonReader jsonReader = new JsonReader(reader)) {
			generateGoals(jsonReader);
		} catch (IOException e) {
			throw new IOException("Error while loading goals from json", e);
		}
	}

	public void generateGoals(JsonReader jsonReader) {
		@SuppressWarnings("unchecked")
		Map<String, GoalDescription> map = (Map<String, GoalDescription>) gsonInstance
			.fromJson(jsonReader, typeOfMapGoal);
		generateGoals(map);
	}

	public void generateGoals(JsonObject jsonInstance) {
		@SuppressWarnings("unchecked")
		Map<String, GoalDescription> map = (Map<String, GoalDescription>) gsonInstance
			.fromJson(jsonInstance, typeOfMapGoal);
		generateGoals(map);
	}

	private void generateGoals(Map<String, GoalDescription> map) {
		if (map == null)
			return;
		dataObject.fillGoalDescriptions(map);
	}

	public void generateGroupMapping(BufferedReader reader) throws IOException {
		try (JsonReader jsonReader = new JsonReader(reader)) {
			jsonReader.setLenient(true);
			generateGroupMapping(jsonReader);
		} catch (IOException e) {
			throw new IOException("Error while loading quest groups from json",
				e);
		}
	}

	public void generateGroupMapping(JsonReader reader) {
		GroupMappingType groupMapping = gsonInstance.fromJson(reader,
			GroupMappingType.class);
		generateGroupMapping(groupMapping);
	}

	public void generateGroupMapping(JsonObject jsonObject) {
		GroupMappingType groupMapping = gsonInstance.fromJson(jsonObject,
			GroupMappingType.class);
		generateGroupMapping(groupMapping);
	}

	private void generateGroupMapping(GroupMappingType groupMapping) {
		if (groupMapping == null)
			return;
		List<String> orderedGroups = groupMapping.orderedGroups;
		Map<String, List<String>> map = groupMapping.mapping;
		if (!orderedGroups.containsAll(map.keySet())) {
			MHFCMain.logger.warn(
				"Detected quest groups which were not included in the ordering. These will appear last in an undefined order. Maybe you are missing the attribute "
					+ MHFCQuestBuildRegistry.KEY_ORDERED_GROUPS);
		}
		if (!map.keySet().containsAll(orderedGroups)) {
			MHFCMain.logger.warn(
				"Detected ordering for quest groups without quests. These will appear empty. Maybe you are missing the attribute "
					+ MHFCQuestBuildRegistry.KEY_GROUP_MAPPING);
		}
		QuestGroupData groupData = new QuestGroupData();
		for (String key : map.keySet())
			groupData.addQuestsToGroup(key, map.get(key));
		groupData.orderGroups(orderedGroups);

		dataObject.addGroups(groupData);
	}

	public void generateQuests(BufferedReader reader) throws IOException {
		try (JsonReader jsonReader = new JsonReader(reader)) {
			generateQuests(jsonReader);
		} catch (IOException e) {
			throw new IOException(
				"Error while loading quest descriptions from json", e);
		}
	}

	public void generateQuests(JsonReader reader) {
		Map<String, QuestDescription> map = gsonInstance.fromJson(reader,
			typeOfMapQuest);
		generateQuests(map);
	}

	public void generateQuests(JsonObject jsonObject) {
		Map<String, QuestDescription> map = gsonInstance.fromJson(jsonObject,
			typeOfMapQuest);
		generateQuests(map);
	}

	private void generateQuests(Map<String, QuestDescription> map) {
		if (map == null)
			return;
		if (map.containsKey(""))
			throw new java.util.InputMismatchException(
				"[MHFC] Quest identifier can not be an empty string");
		dataObject.fillQuestDescriptions(map);
	}

	public JsonElement getGoalsAsJson() {
		return gsonInstance.toJsonTree(dataObject.getFullGoalDescriptionMap(),
			typeOfMapGoal);
	}

	public JsonElement getQuestsAsJson() {
		return gsonInstance.toJsonTree(dataObject.getFullQuestDescriptionMap(),
			typeOfMapQuest);
	}

	public JsonElement getGroupsAsJson() {
		JsonObject holder = new JsonObject();
		JsonElement groupsInOrder = gsonInstance.toJsonTree(dataObject
			.getGroupsInOrder(), typeOfGroupList);
		JsonElement groupMap = gsonInstance.toJsonTree(dataObject
			.getFullGroupMap(), typeOfGroupMap);
		holder.add(MHFCQuestBuildRegistry.KEY_ORDERED_GROUPS, groupsInOrder);
		holder.add(MHFCQuestBuildRegistry.KEY_GROUP_MAPPING, groupMap);
		return holder;
	}
}
