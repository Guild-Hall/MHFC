package mhfc.net.common.core.directors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.data.QuestDescriptionData;
import mhfc.net.common.core.data.QuestDescriptionData.IQuestDescriptionDirector;
import mhfc.net.common.core.data.QuestDescriptionData.QuestGroupData;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;
import mhfc.net.common.quests.api.*;
import mhfc.net.common.quests.api.GoalReference.GoalRefSerializer;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

/**
 * Loads quest descriptions from the file system
 */
public class DirectorLoadQuests implements IQuestDescriptionDirector {
	private static String getJsonObjectStringFieldValueOrDefault(
		JsonObject p_151219_0_, String p_151219_1_, String p_151219_2_) {
		return p_151219_0_.has(p_151219_1_)
			? JsonUtils.getJsonElementStringValue(p_151219_0_.get(p_151219_1_),
				p_151219_1_)
			: p_151219_2_;
	}

	private static int getJsonObjectIntegerFieldValueOrDefault(
		JsonObject p_151208_0_, String p_151208_1_, int p_151208_2_) {
		return p_151208_0_.has(p_151208_1_)
			? JsonUtils.getJsonElementIntegerValue(p_151208_0_.get(p_151208_1_),
				p_151208_1_)
			: p_151208_2_;
	}

	static class QuestSerializer implements JsonDeserializer<QuestDescription> {

		@Override
		public QuestDescription deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonAsObject = JsonUtils.getJsonElementAsJsonObject(json,
				"quest");
			if (!jsonAsObject.has("name")) {
				throw new JsonParseException("Every Quest needs a name");
			} else if (!jsonAsObject.has("goal")) {
				throw new JsonParseException("Every Quest needs a goal");
			} else if (!jsonAsObject.has("reward")) {
				throw new JsonParseException("Every Quest needs a reward");
			} else if (!jsonAsObject.has("fee")) {
				throw new JsonParseException("Every Quest needs a fee");
			} else if (!jsonAsObject.has("areaID")) {
				throw new JsonParseException("Every Quest needs an area");
			} else {
				String goal = null;
				GoalDescription goalDesc = null;
				if (jsonAsObject.get("goal").isJsonPrimitive()) {
					goal = JsonUtils.getJsonElementStringValue(jsonAsObject,
						"goal");
				} else {
					goalDesc = context.deserialize(jsonAsObject.get("goal"),
						GoalDescription.class);
				}
				String name = JsonUtils.getJsonObjectStringFieldValue(
					jsonAsObject, "name");
				int timeLimitInS = getJsonObjectIntegerFieldValueOrDefault(
					jsonAsObject, "timeLimit", 50 * 60);
				String description = getJsonObjectStringFieldValueOrDefault(
					jsonAsObject, "description",
					"A new monster threatens the town so go out and kill it soon.");
				String client = getJsonObjectStringFieldValueOrDefault(
					jsonAsObject, "client", "Hunter Guild");

				String aims = getJsonObjectStringFieldValueOrDefault(
					jsonAsObject, "fails", "Kill all big monsters!");
				String fails = getJsonObjectStringFieldValueOrDefault(
					jsonAsObject, "fails",
					"Died three times or time has run out!");
				String areaId = JsonUtils.getJsonObjectStringFieldValue(
					jsonAsObject, "areaID");

				String typeString = getJsonObjectStringFieldValueOrDefault(
					jsonAsObject, "type", "hunting");
				QuestType type = mhfc.net.common.quests.QuestVisualInformation.QuestType.Hunting;
				switch (typeString) {
					default :
						System.out.println(
							"[MHFC] Type string was not recognized, allowed are hunting, epichunting, gathering and killing\n Falling back to hunting.");
					case "hunting" :
						type = QuestType.Hunting;
						break;
					case "epichunting" :
						type = QuestType.EpicHunting;
						break;
					case "gathering" :
						type = QuestType.Gathering;
						break;
					case "killing" :
						type = QuestType.Killing;
						break;
				}
				int reward = JsonUtils.getJsonObjectIntegerFieldValue(
					jsonAsObject, "reward");
				int fee = JsonUtils.getJsonObjectIntegerFieldValue(jsonAsObject,
					"fee");
				int maxPartySize = getJsonObjectIntegerFieldValueOrDefault(
					jsonAsObject, "maxPartySize", 4);
				String rewardAsString = getJsonObjectStringFieldValueOrDefault(
					jsonAsObject, "rewardAsString", Integer.toString(reward)
						+ "z");
				String feeAsString = getJsonObjectStringFieldValueOrDefault(
					jsonAsObject, "feeAsString", Integer.toString(fee) + "z");
				String timeLimitAsString = getJsonObjectStringFieldValueOrDefault(
					jsonAsObject, "timeLimitAsString", Integer.toString(
						timeLimitInS / 60) + " min" + ((timeLimitInS % 60 == 0)
							? ""
							: " " + Integer.toString(timeLimitInS % 60)
								+ " s"));
				String maxPartySizeAsString = getJsonObjectStringFieldValueOrDefault(
					jsonAsObject, "maxPartySizeAsString", Integer.toString(
						maxPartySize) + " hunters");
				if (goal != null)
					return new QuestDescription(goal, name, type, reward, fee,
						maxPartySize, areaId, description, client, aims, fails,
						rewardAsString, feeAsString, timeLimitAsString,
						maxPartySizeAsString);
				return new QuestDescription(goalDesc, name, type, reward, fee,
					maxPartySize, areaId, description, client, aims, fails,
					rewardAsString, feeAsString, timeLimitAsString,
					maxPartySizeAsString);
			}
		}
	}

	static class GoalSerializer implements JsonDeserializer<GoalDescription> {

		@Override
		public GoalDescription deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
			if (json == null || !json.isJsonObject())
				throw new JsonParseException(
					"Expected a json object for a goal, got a primitive or null");
			JsonObject jsonAsObject = json.getAsJsonObject();
			if (!jsonAsObject.has("type")) {
				throw new JsonParseException("Goal has no type");
			}
			String type = JsonUtils.getJsonObjectStringFieldValue(jsonAsObject,
				"type");
			IGoalFactory gFactory = QuestFactory.getGoalFactory(type);
			return gFactory.buildGoalDescription(jsonAsObject, context);
		}
	}

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

	public static ParameterizedType typeOfGroupList = new ParameterizedType() {
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

	final static Gson gsonInstance = (new GsonBuilder())
		//
		.registerTypeAdapter(GoalDescription.class, new GoalSerializer())
		//
		.registerTypeAdapter(QuestDescription.class, new QuestSerializer())
		//
		.registerTypeAdapter(GoalReference.class, new GoalRefSerializer())
		.create();

	@Override
	public void construct(QuestDescriptionData data) {
		generateQuests(new ResourceLocation(MHFCReference.questLocation), data);
		generateGoals(new ResourceLocation(MHFCReference.goalLocation), data);
		generateGroupMapping(new ResourceLocation(MHFCReference.groupLocation),
			data);

	}

	void generateGoals(ResourceLocation location, QuestDescriptionData data) {
		String pathToRes = "/assets/" + location.getResourceDomain() + "/"
			+ location.getResourcePath();
		try (InputStream input = MHFCQuestBuildRegistry.class
			.getResourceAsStream(pathToRes);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				input))) {
			@SuppressWarnings("unchecked")
			Map<String, GoalDescription> map = (Map<String, GoalDescription>) gsonInstance
				.fromJson(reader, typeOfMapGoal);
			data.fillGoalDescriptions(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void generateGroupMapping(ResourceLocation location,
		QuestDescriptionData data) {
		String pathToRes = "/assets/" + location.getResourceDomain() + "/"
			+ location.getResourcePath();
		try (InputStream input = MHFCQuestBuildRegistry.class
			.getResourceAsStream(pathToRes);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				input))) {
			JsonReader jSonRead = new JsonReader(reader);
			jSonRead.setLenient(true);
			List<String> orderedGroups = gsonInstance.fromJson(jSonRead,
				typeOfGroupList);
			Map<String, List<String>> map = gsonInstance.fromJson(jSonRead,
				typeOfGroupMap);
			if (!orderedGroups.containsAll(map.keySet())) {
				MHFCMain.logger.warn(
					"Detected quest groups which were not included in the ordering. These will appear last in an undefined order.");
			}
			if (map.keySet().containsAll(orderedGroups)) {
				MHFCMain.logger.warn(
					"Detected ordering for quest groups without quests. These will appear empty.");
			}
			QuestGroupData dataObj = new QuestGroupData();
			for (String key : map.keySet())
				dataObj.addQuestsToGroup(key, map.get(key));
			dataObj.orderGroups(orderedGroups);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void generateQuests(ResourceLocation location, QuestDescriptionData data) {
		String pathToRes = "/assets/" + location.getResourceDomain() + "/"
			+ location.getResourcePath();
		try (InputStream input = MHFCQuestBuildRegistry.class
			.getResourceAsStream(pathToRes);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				input))) {
			Map<String, QuestDescription> map = gsonInstance.fromJson(reader,
				typeOfMapQuest);
			if (map.containsKey(""))
				throw new java.util.InputMismatchException(
					"[MHFC] Quest identifier can not be an empty string");
			data.fillQuestDescriptions(map);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
