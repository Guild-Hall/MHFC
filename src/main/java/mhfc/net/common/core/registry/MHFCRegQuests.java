package mhfc.net.common.core.registry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mhfc.net.common.network.packet.MessageQuestScreenInit;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.network.packet.MessageRequestQuestVisual;
import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;
import mhfc.net.common.quests.factory.GoalDescription;
import mhfc.net.common.quests.factory.QuestDescription;
import mhfc.net.common.quests.factory.QuestFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * The registry for quests and quest goals. It will read some source files on
 * init, these war written in the json format. The names for the variables of
 * {@link GoalDescription} are "type", "dependencies" and "arguments" . Only
 * type is necessary, all others are optional as much as the type allows it, see
 * {@link QuestFactory} for further information. For {@link QuestDescription}
 * the names are as following "goal", "name", "reward", "fee", "areaID",
 * "description", "maxPartySize", "timeLimit", "type", "client", "aims",
 * "fails", only the first ones until areaID are mandatory.
 */

public class MHFCRegQuests {

	private static class QuestSerializer
			implements
				JsonDeserializer<QuestDescription> {

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
								"fails",
								"Died three times or time has run out!");
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
								"rewardAsString", Integer.toString(reward)
										+ "z");
				String feeAsString = JsonUtils
						.getJsonObjectStringFieldValueOrDefault(jsonAsObject,
								"feeAsString", Integer.toString(fee) + "z");
				String timeLimitAsString = JsonUtils
						.getJsonObjectStringFieldValueOrDefault(
								jsonAsObject,
								"timeLimitAsString",
								Integer.toString(timeLimitInS / 60)
										+ " min"
										+ ((timeLimitInS % 60 == 0)
												? ""
												: " "
														+ Integer
																.toString(timeLimitInS % 60)
														+ " s"));
				String maxPartySizeAsString = JsonUtils
						.getJsonObjectStringFieldValueOrDefault(jsonAsObject,
								"maxPartySizeAsString",
								Integer.toString(maxPartySize) + " hunters");
				if (goal != null)
					return new QuestDescription(goal, name, type, reward, fee,
							maxPartySize, areaId, description, client, aims,
							fails, rewardAsString, feeAsString,
							timeLimitAsString, maxPartySizeAsString);
				return new QuestDescription(goalDesc, name, type, reward, fee,
						maxPartySize, areaId, description, client, aims, fails,
						rewardAsString, feeAsString, timeLimitAsString,
						maxPartySizeAsString);
			}
		}
	}

	private static class GoalSerializer
			implements
				JsonDeserializer<GoalDescription> {

		@Override
		public GoalDescription deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonAsObject = JsonUtils.getJsonElementAsJsonObject(
					json, "goal");
			if (!jsonAsObject.has("type")) {
				throw new JsonParseException("Goal has no type");
			} else {
				String type = JsonUtils.getJsonObjectStringFieldValue(
						jsonAsObject, "type");
				String[] dependencies = new String[0];
				if (jsonAsObject.has("dependencies")) {
					List<String> list = new ArrayList<String>();
					Iterator<JsonElement> iter = JsonUtils
							.getJsonObjectJsonArrayField(jsonAsObject,
									"dependencies").getAsJsonArray().iterator();
					JsonElement element;
					while (iter.hasNext()) {
						element = iter.next();
						if (JsonUtils.jsonElementTypeIsString(element)) {
							list.add(element.getAsString());
						} else {
							throw new JsonParseException(
									"[MHFC] The array dependencies should be a String array.");
						}
					}
					dependencies = list.toArray(new String[0]);
				}
				Object[] arguments = new String[0];
				if (jsonAsObject.has("arguments")) {
					List<Object> list = new ArrayList<Object>();
					Iterator<JsonElement> iter = JsonUtils
							.getJsonObjectJsonArrayField(jsonAsObject,
									"arguments").getAsJsonArray().iterator();
					JsonElement element;
					while (iter.hasNext()) {
						element = iter.next();
						if (element.isJsonPrimitive()) {
							list.add(element.getAsString());
						} else {
							context.deserialize(element, typeOfMapGoal);
						}
					}
					arguments = list.toArray(new Object[0]);
				}
				return new GoalDescription(type, dependencies, arguments);
			}
		}
	}

	public static class RegistryRequestVisualHandler
			implements
				IMessageHandler<MessageRequestQuestVisual, MessageQuestVisual> {

		@Override
		public MessageQuestVisual onMessage(MessageRequestQuestVisual message,
				MessageContext ctx) {
			String identifier = message.getIdentifier();
			QuestDescription description = getQuestDescription(identifier);
			QuestVisualInformation info = (description == null
					? QuestVisualInformation.IDENTIFIER_ERROR
					: description.getVisualInformation());
			String[] stringArray = {identifier, info.getName(),
					info.getDescription(), info.getClient(), info.getAims(),
					info.getFails(), info.getAreaID(),
					info.getTimeLimitAsString(), info.getType().getAsString(),
					info.getRewardString(), info.getFeeString(),
					info.getMaxPartySize()};
			return new MessageQuestVisual(stringArray);
		}
	}

	public static class PlayerConnectionHandler {

		@SubscribeEvent
		public void onPlayerJoin(PlayerLoggedInEvent logIn) {
			System.out
					.println("[MHFC] Sent new identifier configuration to client");
			networkWrapper.sendTo(new MessageQuestScreenInit(groupMapping,
					groupIDs), (EntityPlayerMP) logIn.player);
		}

		@SubscribeEvent
		public void onPlayerLeave(PlayerLoggedOutEvent logOut) {
			playerQuest.get(logOut.player).leavePlayer(logOut.player);
		}
	}

	private static final ParameterizedType typeOfMapGoal = new ParameterizedType() {
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

	private static final ParameterizedType typeOfMapQuest = new ParameterizedType() {
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
	private static final ParameterizedType typeOfGroupMap = new ParameterizedType() {
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

	private static ParameterizedType typeOfGroupList = new ParameterizedType() {
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

	// TODO Externalize into util
	public static final String questLocation = "mhfc:quests/quests.json";
	public static final String goalLocation = "mhfc:quests/goals.json";
	public static final String groupLocation = "mhfc:quests/groups.json";

	public static final HashMap<String, QuestDescription> questDescriptions = new HashMap<String, QuestDescription>();
	public static final Map<String, List<String>> groupMapping = new HashMap<String, List<String>>();
	public static final List<String> groupIDs = new ArrayList<String>();
	public static final HashMap<String, GoalDescription> goalDescriptions = new HashMap<String, GoalDescription>();

	protected static HashMap<EntityPlayer, GeneralQuest> playerQuest = new HashMap<EntityPlayer, GeneralQuest>();
	protected static List<GeneralQuest> questsRunning = new ArrayList<GeneralQuest>();

	private final static Gson gsonInstance = (new GsonBuilder())
			.registerTypeAdapter(GoalDescription.class, new GoalSerializer())
			.registerTypeAdapter(QuestDescription.class, new QuestSerializer())
			.create();

	public static final String QUEST_TYPE_HUNTING = "quests.type.hunting";
	public static final String QUEST_TYPE_GATHERING = "quests.type.gathering";
	public static final String QUEST_TYPE_EPIC_HUNTING = "quest.type.epichunting";
	public static final String QUEST_TYPE_KILLING = "quests.type.killing";

	// FIXME use our MHFC wide networkWrapper
	public static final SimpleNetworkWrapper networkWrapper = new SimpleNetworkWrapper(
			"MHFC:Quests");
	public static final int discriminator_answer = 130;
	public static final int discriminator_join = 131;

	public static void init() {
		generateQuests(new ResourceLocation(questLocation));
		generateGoals(new ResourceLocation(goalLocation));
		generateGroupMapping(new ResourceLocation(groupLocation));
		System.out.println("[MHFC] Number of goals loaded: "
				+ goalDescriptions.size());
		System.out.println("[MHFC] Number of quests loaded: "
				+ questDescriptions.size());
		// QuestFactory.constructQuest(questDescriptions.get("TestQuest"),
		// null);
		networkWrapper.registerMessage(RegistryRequestVisualHandler.class,
				MessageRequestQuestVisual.class, discriminator_answer,
				Side.SERVER);
		FMLCommonHandler.instance().bus()
				.register(new PlayerConnectionHandler());
	}

	private static void generateGoals(ResourceLocation location) {
		if (location == null) {
		} else {
			try (InputStream input = Minecraft.getMinecraft()
					.getResourceManager().getResource(location)
					.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(input))) {
				Map<String, GoalDescription> map = (Map<String, GoalDescription>) gsonInstance
						.fromJson(reader, typeOfMapGoal);
				for (String qualifier : map.keySet()) {
					goalDescriptions.put(qualifier, map.get(qualifier));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void generateGroupMapping(ResourceLocation location) {
		if (location == null) {
		} else {
			try (InputStream input = Minecraft.getMinecraft()
					.getResourceManager().getResource(location)
					.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(input))) {
				JsonReader jSonRead = new JsonReader(reader);
				jSonRead.setLenient(true);
				List<String> list = (List<String>) gsonInstance.fromJson(
						jSonRead, typeOfGroupList);
				Map<String, List<String>> map = (Map<String, List<String>>) gsonInstance
						.fromJson(jSonRead, typeOfGroupMap);
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

	//@formatter:off
	/**
	 * Get the next string of characters that is considered a word. Words are
	 * divided by whitespace character chars, commas and semicolons, or all
	 * types of brackets and a ':'. If a non-whitespace character divides two
	 * words, it itself is also considered a word. Whenever characters appear in
	 * quotation marks, everything within is treated as one single word,
	 * including all whitespace characters, but not the quotation marks. A
	 * quotation mark looses its meaning if there is a \ in front of it.
	 * 
	 * @return Returns the next word or null if the end of file was reached.
	 * @throws IOException
	 *             Passed on from reader.read()
	 *
	private static String getNextWord(BufferedReader reader) throws IOException {
		char a = getChar(reader);
		while (a != ((char) -1) && Character.isWhitespace(a)) {
			a = getChar(reader);
		}
		if (a < 0) {
			System.out.println("Null, becuase of eof");
			return null;
		} else {
			if (isBracketOrComma(a) || (a == ':')) {
				return Character.toString(a);
			} else if (a == '"') {
				String ret = "";
				boolean validNow = false;
				boolean validNext = true;
				while (a != ((char) -1) && !(a == '"' && validNow)) {
					ret += a;
					a = getChar(reader);
					validNow = validNext;
					validNext = !(a == '\\');
				}
				return ret.substring(1);
			} else {
				String ret = "";
				while (a != ((char) -1) && !Character.isWhitespace(a)
						&& !isBracketOrComma(a) && !(a == ':')) {
					ret += a;
					reader.mark(1);
					a = getChar(reader);
				}
				reader.reset();
				return ret;
			}
		}
	}

	private static char getChar(BufferedReader reader) throws IOException {
		char a = (char) reader.read();
		if (a == '\n') {
			++currentLine;
		}
		return a;
	}

	/**
	 * Determines if a character is a bracket, a comma or a semicolon.
	 * 
	 *
	private static boolean isBracketOrComma(char a) {
		return a == '{' || a == '}' || a == '(' || a == ')' || a == '['
				|| a == ']' || a == ',' || a == ';';
	}

	private static boolean isBracketOrComma(String s) {
		return (s != null) && s.length() == 1 && isBracketOrComma(s.charAt(0));
	}

	private static String getNextLine(BufferedReader reader) throws IOException {
		String retString = "";
		do {
			retString = reader.readLine();
			++currentLine;
		} while (retString != null
				&& (retString.startsWith("//") || retString.startsWith("#")));
		return retString;
	}*/
	//@formatter:on

	private static void generateQuests(ResourceLocation location) {
		if (location == null) {
		} else {
			try (InputStream input = Minecraft.getMinecraft()
					.getResourceManager().getResource(location)
					.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(input))) {
				Map<String, QuestDescription> map = (Map) gsonInstance
						.fromJson(reader, typeOfMapQuest);
				for (String qualifier : map.keySet()) {
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

	/**
	 * Get the quest on which a player is on. If the player is on no quest then
	 * null is returned.
	 */
	public static GeneralQuest getQuestForPlayer(EntityPlayer player) {
		return playerQuest.get(player);
	}

	/**
	 * Returns all quests that are running at the moment.
	 */
	public static List<GeneralQuest> getRunningQuests() {
		return questsRunning;
	}

	/**
	 * Sets the quest for a player, use null to remove the entry
	 * 
	 */
	public static void setQuestForPlayer(EntityPlayer player,
			GeneralQuest generalQuest) {
		if (generalQuest == null)
			playerQuest.remove(player);
		else
			playerQuest.put(player, generalQuest);
	}

	public static void registerQuest(GeneralQuest generalQuest) {
		questsRunning.add(generalQuest);
	}
}
