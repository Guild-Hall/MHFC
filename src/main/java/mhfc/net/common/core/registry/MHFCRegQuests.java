package mhfc.net.common.core.registry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.GoalDescription;
import mhfc.net.common.quests.QuestDescription;
import mhfc.net.common.quests.QuestFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * The registry for quests and quest goals. It will read some source files on
 * init, the specifications of these will come very soon, just use json and you
 * should be fine. The names for the variables of quest goals are "type",
 * "dependencyIDs" and "arguments". Only type is necessary, all others are
 * optional as much as the type allows it, see {@link QuestFactory} for further
 * information. For quests the names are as following "goalID", "name", "type",
 * "reward", "fee", "maxPartySize", "timeLimit", "areaID", "description",
 * "client", "aims", "fails", only the first ones until areaID are mandatory.
 */
public class MHFCRegQuests {

	public static final String questLocation = "mhfc:quests/quests.json";
	public static final String goalLocation = "mhfc:quests/goals.json";

	public static final HashMap<String, QuestDescription> questDescriptions = new HashMap<String, QuestDescription>();
	public static final HashMap<String, GoalDescription> goalDescriptions = new HashMap<String, GoalDescription>();

	protected static HashMap<EntityPlayer, GeneralQuest> playerQuest = new HashMap<EntityPlayer, GeneralQuest>();
	protected static List<GeneralQuest> quests = new ArrayList<GeneralQuest>();
	private static int currentLine;

	public static void init() {
		generateQuests(new ResourceLocation(questLocation));
		try {
			generateGoals(new ResourceLocation(goalLocation));
		} catch (ParseException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}
		System.out.println("Goals: " + goalDescriptions.size());
		GoalDescription d = goalDescriptions.get("Test");
		System.out.println(String.format("%s %s %s", d.getGoalType(),
				d.getArguments()[0], d.getArguments()[1]));
	}

	private enum GoalReadState {
		ReadName, ReadBodyBeginning, ReadBody, Finished, Invalid;
	}

	private static void generateGoals(ResourceLocation location)
			throws ParseException {
		if (location == null) {
		} else {
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(Minecraft.getMinecraft()
							.getResourceManager().getResource(location)
							.getInputStream()))) {
				GoalReadState state = GoalReadState.ReadName;
				String word = null;
				currentLine = 1;
				String name = null;
				String goalType = null;
				List<String> dependencies = null;
				List<String> arguments = null;
				boolean firstElementOfBody = true;
				while (state != GoalReadState.Finished) {
					switch (state) {
						case ReadName :
							System.out.println("Reading name");
							if (name != null) {
								throw new ParseException(
										String.format(
												"[MHFS] Exception while reading name, found a second name line before object body, in line %i",
												currentLine), currentLine);
							}
							word = getNextWord(reader);
							if (isBracketOrComma(word))
								throw new ParseException(
										String.format(
												"[MHFS] Goal needs a name, can not start with this character, in line %i",
												currentLine), currentLine);
							else {
								if (word == null)
									state = GoalReadState.Finished;
								else {
									name = word;
									state = GoalReadState.ReadBodyBeginning;
								}
							}
							break;
						case ReadBodyBeginning :
							System.out.println("Reading body beginning");
							word = getNextWord(reader);
							if (!word.equals(":")) {
								throw new ParseException(
										String.format(
												"[MHFS] Body must be preceeded with :, in line %s",
												currentLine + ""), currentLine);
							}
							word = getNextWord(reader);
							if (!word.equals("{"))
								throw new ParseException(
										String.format(
												"[MHFS] Body must start with {, in line %s",
												currentLine + ""), currentLine);
							state = GoalReadState.ReadBody;
							break;
						case ReadBody :
							System.out.println("Reading body");
							word = getNextWord(reader);
							if (word.equals("}")) {
								if (goalType == null)
									throw new ParseException(
											String.format(
													"[MHFS] Goal needs at least a type, in line %s",
													currentLine + ""),
											currentLine);
								else {
									if (arguments == null)
										arguments = new ArrayList<String>();
									if (dependencies == null)
										dependencies = new ArrayList<String>();
									goalDescriptions
											.put(name,
													new GoalDescription(
															goalType,
															dependencies
																	.toArray(new String[0]),
															arguments.toArray()));
									state = GoalReadState.ReadName;
									name = null;
									goalType = null;
									arguments = null;
									dependencies = null;
									firstElementOfBody = true;
								}
							} else {
								if (!word.equals(",") && !word.equals(";")
										&& !firstElementOfBody)
									throw new ParseException(
											String.format(
													"[MHFS] Use comma or semicolon in between key-value pairs, in line %s",
													currentLine + ""),
											currentLine);
								if (!firstElementOfBody)
									word = getNextWord(reader);
								firstElementOfBody = false;
								switch (word) {
									case "type" :
										if (!getNextWord(reader).equals(":"))
											throw new ParseException(
													String.format(
															"[MHFS] keyword not followed by :, in line %s",
															currentLine + ""),
													currentLine);
										word = getNextWord(reader);
										if (isBracketOrComma(word))
											throw new ParseException(
													String.format(
															"[MHFS] Type may only be a string, in line %s",
															currentLine + ""),
													currentLine);
										goalType = word;
										break;
									case "dependencyIDs" :
										String next1,
										next2;
										next1 = getNextWord(reader);
										next2 = getNextWord(reader);
										if ((!next1.equals(":"))
												|| (!next2.equals("[")))
											throw new ParseException(
													String.format(
															"[MHFS] dependencyIDs is an array, use :[], in line %s",
															currentLine + ""),
													currentLine);
										else {
											dependencies = new ArrayList<String>();
											word = getNextWord(reader);
											if (!word.equals("]")) {
												if (isBracketOrComma(word))
													throw new ParseException(
															String.format(
																	"[MHFS] List has to begin with an element, in line %s",
																	currentLine
																			+ ""),
															currentLine);
												dependencies.add(word);
											}
											while (!word.equals("]")) {
												if (!word.equals(",")
														&& !word.equals(";"))
													throw new ParseException(
															String.format(
																	"[MHFS] Use comma or semicolon in between values, in line %s",
																	currentLine
																			+ ""),
															currentLine);
												word = getNextWord(reader);
												if (isBracketOrComma(word))
													throw new ParseException(
															String.format(
																	"[MHFS] Brackets, commas and semicolons may not be used as values, in line %s",
																	currentLine
																			+ ""),
															currentLine);
												dependencies.add(word);
												word = getNextWord(reader);
											}
										}
										break;
									case "arguments" :
										next1 = getNextWord(reader);
										next2 = getNextWord(reader);
										if (!next1.equals(":")
												|| !next2.equals("["))
											throw new ParseException(
													String.format(
															"[MHFS] arguments is an array, use :[] instead of %s %s, in line %s",
															next1, next2,
															currentLine + ""),
													currentLine);
										else {
											arguments = new ArrayList<String>();
											word = getNextWord(reader);
											if (!word.equals("]")) {
												if (isBracketOrComma(word))
													throw new ParseException(
															String.format(
																	"[MHFS] List has to begin with an element, in line %s",
																	currentLine
																			+ ""),
															currentLine);
												arguments.add(word);
											}
											word = getNextWord(reader);
											while (!word.equals("]")) {
												if (!word.equals(",")
														&& !word.equals(";"))
													throw new ParseException(
															String.format(
																	"[MHFS] Use comma or semicolon in between values instead of %s, in line %s",
																	word,
																	currentLine
																			+ ""),
															currentLine);
												word = getNextWord(reader);
												if (isBracketOrComma(word))
													throw new ParseException(
															String.format(
																	"[MHFS] Brackets, commas and semicolons may not be used as values, in line %s",
																	currentLine
																			+ ""),
															currentLine);
												arguments.add(word);
												word = getNextWord(reader);
											}
										}
										break;
									default :
										throw new ParseException(
												String.format(
														"[MHFS] %s is not a keyword, in line %s",
														word, currentLine + ""),
												currentLine);
								}
							}
							break;
						case Finished :
							System.out.println("Finished reading file");
							break;
						case Invalid :
						default :
							throw new ParseException(
									String.format(
											"[MHFS] File %s is not anywhere near being a goal file",
											location.getResourcePath()),
									currentLine);
					}
				}

			} catch (IOException ex) {

			}
		}
	}

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
	 */
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
	 */
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
	}

	private static void generateQuests(ResourceLocation location) {
		// TODO Auto-generated method stub

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
		return quests;
	}

	public static void setQuestForPlayer(EntityPlayer player,
			GeneralQuest generalQuest) {
		playerQuest.put(player, generalQuest);
	}

	public static void registerQuest(GeneralQuest generalQuest) {
		quests.add(generalQuest);
	}

	public String getQuestFileLocations() {
		return questLocation;
	}

	public String getGoalFileLocations() {
		return goalLocation;
	}
}
