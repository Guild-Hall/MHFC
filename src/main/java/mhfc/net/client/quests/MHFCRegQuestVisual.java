package mhfc.net.client.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mhfc.net.client.gui.quests.GuiQuestBoard;
import mhfc.net.client.gui.quests.GuiQuestGiver;
import mhfc.net.client.gui.quests.QuestStatusDisplay;
import mhfc.net.common.core.registry.MHFCRegQuests;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageQuestRunningSubscription;
import mhfc.net.common.network.packet.MessageQuestScreenInit;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.network.packet.MessageRequestQuestVisual;
import mhfc.net.common.quests.QuestRunningInformation;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MHFCRegQuestVisual {

	public static class QuestVisualInitHandler
			implements
				IMessageHandler<MessageQuestScreenInit, IMessage> {

		@Override
		public IMessage onMessage(MessageQuestScreenInit message,
				MessageContext ctx) {
			Map<String, List<String>> identifierLists = message
					.getIdentifierListMap();
			List<String> identifiers = message.getIdentifiers();
			groupIDsInOrder = identifiers;
			groupIDToListMap = identifierLists;

			// System.out
			// .println("[MHFC] Received new identifier configuration from server, "
			// + groupIDsInOrder.size() + " groups");

			return null;
		}
	}

	public static class QuestScreenVisualHandler
			implements
				IMessageHandler<MessageQuestVisual, IMessage> {

		@Override
		public IMessage onMessage(MessageQuestVisual message, MessageContext ctx) {
			switch (message.getTypeID()) {
				case 2 :
				case 1 :
				case 0 :
					String[] strings = message.getStrings();
					String identifier = strings[0];
					String name = strings[1];
					String description = strings[2];
					String client = strings[3];
					String aims = strings[4];
					String fails = strings[5];
					String areaNameID = strings[6];
					String timeLimitInS = strings[7];
					String type = strings[8];
					String reward = strings[9];
					String fee = strings[10];
					String maxPartySize = strings[11];
					QuestType realType = null;
					switch (type) {
						case MHFCRegQuests.QUEST_TYPE_KILLING :
							realType = QuestType.Killing;
							break;
						case MHFCRegQuests.QUEST_TYPE_HUNTING :
							realType = QuestType.Hunting;
							break;
						case MHFCRegQuests.QUEST_TYPE_EPIC_HUNTING :
							realType = QuestType.EpicHunting;
							break;
						case MHFCRegQuests.QUEST_TYPE_GATHERING :
							realType = QuestType.Gathering;
							break;
						default :
					}
					QuestVisualInformation visual;
					if (!name.equals("")) {
						visual = new QuestVisualInformation(name, description,
								client, aims, fails, areaNameID, timeLimitInS,
								reward, fee, maxPartySize, realType);
					} else {
						visual = null;
					}
					switch (message.getTypeID()) {
						case 0 :
							identifierToVisualInformationMap.put(identifier,
									visual);
							break;
						case 1 :
							hasPlayerQuest = (visual != null);
							QuestRunningInformation runInfo = new QuestRunningInformation(
									visual, strings[12], strings[13]);
							if (playersVisual != null) {
								playersVisual.cleanUp();
							}
							playersVisual = (!hasPlayerQuest) ? null : runInfo;
							// System.out.println("[MHFC] "
							// + (visual == null
							// ? "No quest active"
							// : "Got a new visual to")
							// + " set for the display");
							break;
						case 2 :
							boolean clear = visual == null;
							runInfo = new QuestRunningInformation(visual,
									strings[12], strings[13]);
							if (clear) {
								identifierToVisualRunningMap.remove(identifier);
								questBoard.removeQuest(identifier);
							} else {
								identifierToVisualRunningMap.put(identifier,
										runInfo);
								questBoard.addQuest(identifier, runInfo);
							}
					}
					break;
			}
			return null;
		}
	}

	// FIXME outsource a lot of this into the main registry to avoid duplication
	// and confusion
	public static final String UNLOCALIZED_TAG_FEE = "mhfc.quests.visual.tag.fee";
	public static final String UNLOCALIZED_TAG_REWARD = "mfhc.quests.visual.tag.reward";
	public static final String UNLOCALIZED_TAG_TIME = "mhfc.quests.visual.tag.time";
	public static final String UNLOCALIZED_TAG_AREA = "mhfc.quests.visual.tag.area";
	public static final String UNLOCALIZED_TAG_AIMS = "mhfc.quests.visual.tag.aims";
	public static final String UNLOCALIZED_TAG_FAILS = "mhfc.quests.visual.tag.fails";
	public static final String UNLOCALIZED_TAG_CLIENT = "mhfc.quests.visual.tag.client";
	public static final String UNLOCALIZED_TAG_DESCRIPTION = "mhfc.quests.visual.tag.description";
	public static final String UNLOCALIZED_TAG_MONSTERS = "mhfc.quests.visual.tag.monsters";
	public static final String UNLOCALIZED_TAG_REQUISITES = "mhfc.quests.visual.tag.requisites";
	public static final String UNLOCALIZED_TAG_STATUS_SHORT = "mhfc.quests.visual.tag.statusshort";
	public static final String UNLOCALIZED_TAG_STATUS_LONG = "mhfc.quests.visual.tag.statuslong";

	// FIXME change the texture
	public static final ResourceLocation QUEST_STATUS_INVENTORY_BACKGROUND = new ResourceLocation(
			"textures/gui/demo_background.png");
	public static final ResourceLocation QUEST_STATUS_ONSCREEN_BACKGROUND = new ResourceLocation(
			"textures/gui/demo_background.png");
	public static final ResourceLocation QUEST_BOARD_BACKGROUND = new ResourceLocation(
			"textures/gui/demo_background.png");

	private static Map<String, List<String>> groupIDToListMap = new HashMap<String, List<String>>();
	private static List<String> groupIDsInOrder = new ArrayList<String>();
	private static Map<String, QuestVisualInformation> identifierToVisualInformationMap = new HashMap<String, QuestVisualInformation>();
	private static Map<String, QuestRunningInformation> identifierToVisualRunningMap = new HashMap<String, QuestRunningInformation>();
	private static QuestStatusDisplay display;
	private static GuiQuestBoard questBoard = new GuiQuestBoard(
			Minecraft.getMinecraft().thePlayer);
	private static boolean hasPlayerQuest = false;
	private static QuestRunningInformation playersVisual;

	private static PacketPipeline pipeline = MHFCRegQuests.pipeline;

	public static GuiQuestGiver getScreen(int i, EntityPlayer playerEntity) {
		if (i < 0 || i >= groupIDToListMap.size())
			return null;
		List<String> list = new ArrayList<String>(groupIDToListMap.keySet());
		return new GuiQuestGiver(list.toArray(new String[0]), playerEntity);
	}

	public static List<String> getIdentifierList(String groupId) {
		return groupIDToListMap.get(groupId);
	}

	/**
	 * 
	 * @param identifier
	 *            of the quest in which you are interested
	 * @return Either the visual representation of the requested quest or a
	 *         replacement <br>
	 *         representing loading.
	 */
	public static QuestVisualInformation getVisualInformation(String identifier) {
		if (identifierToVisualInformationMap.containsKey(identifier)) {
			return identifierToVisualInformationMap.get(identifier);
		}
		pipeline.networkPipe.sendToServer(new MessageRequestQuestVisual(
				identifier));
		identifierToVisualInformationMap.put(identifier,
				QuestVisualInformation.LOADING_REPLACEMENT);
		return QuestVisualInformation.LOADING_REPLACEMENT;
	}

	public static QuestRunningInformation getRunningInformation(
			String identifier) {
		return identifierToVisualRunningMap.get(identifier);
	}

	public static void setAndSendRunningListenStatus(boolean newStatus,
			EntityPlayer forPlayer) {
		pipeline.networkPipe.sendToServer(new MessageQuestRunningSubscription(
				newStatus, forPlayer));
		if (!newStatus) {
			questBoard.clearList();
		}
	}

	public static boolean hasPlayerQuest() {
		return hasPlayerQuest;
	}

	public static void init() {
		display = new QuestStatusDisplay();
		pipeline.registerPacket(QuestVisualInitHandler.class,
				MessageQuestScreenInit.class, Side.CLIENT);
		pipeline.registerPacket(QuestScreenVisualHandler.class,
				MessageQuestVisual.class, Side.CLIENT);
		MinecraftForge.EVENT_BUS.register(display);

	}

	public static QuestRunningInformation getPlayerVisual() {
		return playersVisual;
	}

	public static GuiQuestBoard getQuestBoard() {
		return questBoard;
	}
}
