package mhfc.net.common.core.registry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import mhfc.net.MHFCMain;
import mhfc.net.common.eventhandler.MHFCInteractionHandler.MHFCInteractionModReloadEvent;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageQuestScreenInit;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;
import mhfc.net.common.quests.api.*;
import mhfc.net.common.quests.api.GoalReference.GoalRefSerializer;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * The registry for quests and quest goals. It will read some source files on
 * init, these war written in the json format. The name for the primary variable
 * of {@link GoalDescription} is "type", see {@link QuestFactory} for further
 * information.<br>
 * For {@link QuestDescription} the names are as following: "goal", "name",
 * "reward", "fee", "areaID", "description", "maxPartySize", "timeLimit",
 * "type", "client", "aims", "fails", only the ones until areaID are mandatory.
 */

public class MHFCQuestBuildRegistry {

	static class QuestSerializer implements JsonDeserializer<QuestDescription> {

		@Override
		public QuestDescription deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonAsObject = JsonUtils.getJsonElementAsJsonObject(
				json, "quest");
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
				int timeLimitInS = JsonUtils
					.getJsonObjectIntegerFieldValueOrDefault(jsonAsObject,
						"timeLimit", 50 * 60);
				String description = JsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonAsObject,
						"description",
						"A new monster threatens the town so go out and kill it soon.");
				String client = JsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonAsObject,
						"client", "Hunter Guild");;
				String aims = JsonUtils.getJsonObjectStringFieldValueOrDefault(
					jsonAsObject, "fails", "Kill all big monsters!");
				String fails = JsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonAsObject,
						"fails", "Died three times or time has run out!");
				String areaId = JsonUtils.getJsonObjectStringFieldValue(
					jsonAsObject, "areaID");

				String typeString = JsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonAsObject,
						"type", "hunting");
				QuestType type = mhfc.net.common.quests.QuestVisualInformation.QuestType.Hunting;
				switch (typeString) {
					default :
						System.out
							.println("[MHFC] Type string was not recognized, allowed are hunting, epichunting, gathering and killing\n Falling back to hunting.");
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
				int fee = JsonUtils.getJsonObjectIntegerFieldValue(
					jsonAsObject, "fee");
				int maxPartySize = JsonUtils
					.getJsonObjectIntegerFieldValueOrDefault(jsonAsObject,
						"maxPartySize", 4);
				String rewardAsString = JsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonAsObject,
						"rewardAsString", Integer.toString(reward) + "z");
				String feeAsString = JsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonAsObject,
						"feeAsString", Integer.toString(fee) + "z");
				String timeLimitAsString = JsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonAsObject,
						"timeLimitAsString", Integer
							.toString(timeLimitInS / 60)
							+ " min"
							+ ((timeLimitInS % 60 == 0) ? "" : " "
								+ Integer.toString(timeLimitInS % 60) + " s"));
				String maxPartySizeAsString = JsonUtils
					.getJsonObjectStringFieldValueOrDefault(jsonAsObject,
						"maxPartySizeAsString", Integer.toString(maxPartySize)
							+ " hunters");
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
	//@formatter:off
	public static final ParameterizedType typeOfGroupMap = new ParameterizedType() {
		@Override
		public Type[] getActualTypeArguments() {
			return new Type[]{
					String.class, // Don't go weird on me formatter
					new ParameterizedType() {
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
					}//
			};
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
	//@formatter:on
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

	public static final HashMap<String, QuestDescription> questDescriptions = new HashMap<String, QuestDescription>();
	public static final Map<String, List<String>> groupMapping = new HashMap<String, List<String>>();
	public static final List<String> groupIDs = new ArrayList<String>();
	public static final HashMap<String, GoalDescription> goalDescriptions = new HashMap<String, GoalDescription>();

	final static Gson gsonInstance = (new GsonBuilder())
		//
		.registerTypeAdapter(GoalDescription.class,
			new MHFCQuestBuildRegistry.GoalSerializer())
		//
		.registerTypeAdapter(QuestDescription.class,
			new MHFCQuestBuildRegistry.QuestSerializer())
		//
		.registerTypeAdapter(GoalReference.class, new GoalRefSerializer())
		.create();

	public static final String GOAL_CHAIN_TYPE = "chain";
	public static final String GOAL_FORK_TYPE = "fork";
	public static final String GOAL_DEATH_RESTRICTION_TYPE = "death";
	public static final String GOAL_HUNTING_TYPE = "hunting";
	public static final String GOAL_TIME_TYPE = "time";

	public static final String QUEST_TYPE_HUNTING = "mhfc.quests.type.hunting";
	public static final String QUEST_TYPE_GATHERING = "mhfc.quests.type.gathering";
	public static final String QUEST_TYPE_EPIC_HUNTING = "mhfc.quests.type.epichunting";
	public static final String QUEST_TYPE_KILLING = "mhfc.quests.type.killing";

	public static void init() {
		loadQuests();
		FMLCommonHandler.instance().bus()
			.register(new MHFCQuestBuildRegistry());
	}

	@SubscribeEvent
	void onModReload(MHFCInteractionModReloadEvent event) {
		MHFCQuestBuildRegistry.questDescriptions.clear();
		MHFCQuestBuildRegistry.goalDescriptions.clear();
		MHFCQuestBuildRegistry.groupIDs.clear();
		MHFCQuestBuildRegistry.groupMapping.clear();
		MHFCQuestBuildRegistry.loadQuests();

		Iterator<?> it = FMLCommonHandler.instance()
			.getMinecraftServerInstance().getConfigurationManager().playerEntityList
			.iterator();
		while (it.hasNext()) {
			EntityPlayerMP player = (EntityPlayerMP) it.next();
			PacketPipeline.networkPipe.sendTo(new MessageQuestScreenInit(
				MHFCQuestBuildRegistry.groupMapping,
				MHFCQuestBuildRegistry.groupIDs), player);
		}
	}

	public static void loadQuests() {
		MHFCQuestBuildRegistry.generateQuests(new ResourceLocation(
			MHFCReference.questLocation));
		MHFCQuestBuildRegistry.generateGoals(new ResourceLocation(
			MHFCReference.goalLocation));
		MHFCQuestBuildRegistry.generateGroupMapping(new ResourceLocation(
			MHFCReference.groupLocation));

		MHFCMain.logger.info("Quest loaded");
	}

	static void generateGoals(ResourceLocation location) {
		if (location == null) {
		} else {
			try (InputStream input = Minecraft.getMinecraft()
				.getResourceManager().getResource(location).getInputStream();
				BufferedReader reader = new BufferedReader(
					new InputStreamReader(input))) {
				@SuppressWarnings("unchecked")
				Map<String, GoalDescription> map = (Map<String, GoalDescription>) gsonInstance
					.fromJson(reader, MHFCQuestBuildRegistry.typeOfMapGoal);
				for (String qualifier : map.keySet()) {
					goalDescriptions.put(qualifier, map.get(qualifier));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static void generateGroupMapping(ResourceLocation location) {
		if (location == null) {
		} else {
			try (InputStream input = Minecraft.getMinecraft()
				.getResourceManager().getResource(location).getInputStream();
				BufferedReader reader = new BufferedReader(
					new InputStreamReader(input))) {
				JsonReader jSonRead = new JsonReader(reader);
				jSonRead.setLenient(true);
				List<String> list = gsonInstance.fromJson(jSonRead,
					MHFCQuestBuildRegistry.typeOfGroupList);
				Map<String, List<String>> map = gsonInstance.fromJson(jSonRead,
					MHFCQuestBuildRegistry.typeOfGroupMap);
				if (!(map.keySet().containsAll(list) && list.containsAll(map
					.keySet()))) {
					throw new JsonParseException(
						"[MHFC] Group identifier list and mapping keys have to contain the same elements");
				}
				groupIDs.addAll(list);
				groupMapping.putAll(map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static void generateQuests(ResourceLocation location) {
		if (location == null) {
		} else {
			try (InputStream input = Minecraft.getMinecraft()
				.getResourceManager().getResource(location).getInputStream();
				BufferedReader reader = new BufferedReader(
					new InputStreamReader(input))) {
				Map<String, QuestDescription> map = gsonInstance.fromJson(
					reader, MHFCQuestBuildRegistry.typeOfMapQuest);
				for (String qualifier : map.keySet()) {
					if (qualifier.equals("")) {
						throw new java.util.InputMismatchException(
							"[MHFC] Quest identifier can not be an empty string");
					}
					questDescriptions.put(qualifier, map.get(qualifier));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static QuestDescription getQuestDescription(String id) {
		QuestDescription qd = questDescriptions.get(id);
		return qd;
	}

	public static GoalDescription getGoalDescription(String id) {
		GoalDescription qd = goalDescriptions.get(id);
		return qd;
	}

}
