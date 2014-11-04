package mhfc.net.client.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mhfc.net.client.gui.quests.GuiQuestGiver;
import mhfc.net.client.gui.quests.QuestStatusDisplay;
import mhfc.net.common.core.registry.MHFCRegQuests;
import mhfc.net.common.network.packet.MessageQuestScreenInit;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.network.packet.MessageRequestQuestVisual;
import mhfc.net.common.quests.QuestRunningInformation;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
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
			System.out
					.println("[MHFC] Received new identifier configuration from server, "
							+ groupIDsInOrder.size() + " groups");
			return null;
		}
	}

	public static class QuestScreenVisualHandler
			implements
				IMessageHandler<MessageQuestVisual, IMessage> {

		@Override
		public IMessage onMessage(MessageQuestVisual message, MessageContext ctx) {
			switch (message.getTypeID()) {
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
							throw new IllegalStateException(
									"[MHFC] Exception decoding QuestVisualMessage, Type unknown");
					}
					QuestVisualInformation visual;
					if (!identifier.equals("")) {
						visual = new QuestVisualInformation(name, description,
								client, aims, fails, areaNameID, timeLimitInS,
								reward, fee, maxPartySize, realType);
					} else {
						visual = null;
					}
					if (message.getTypeID() == 0) {
						identifierToVisualInformationMap
								.put(identifier, visual);
						System.out
								.println("[MHFC] Assigned a new static visual to "
										+ identifier);
					} else {
						display.setRunningInformation(visual == null
								? null
								: new QuestRunningInformation(visual,
										strings[12], strings[13]));
						hasPlayerQuest = (visual != null);
						System.out.println("[MHFC] "
								+ (visual == null
										? "No quest active"
										: "Got a new visual to")
								+ " set for the display");
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

	// FIXME Fix to a non-random chosen discriminator
	public static final int discriminator_initMessage = 128;
	public static final int discriminator_visualMessage = 129;
	public static final ResourceLocation QUEST_STATUS_INVENTORY_BACKGROUND = new ResourceLocation(
			"textures/gui/demo_background.png");
	public static final ResourceLocation QUEST_STATUS_ONSCREEN_BACKGROUND = new ResourceLocation(
			"textures/gui/demo_background.png");

	private static Map<String, List<String>> groupIDToListMap = new HashMap<String, List<String>>();
	private static List<String> groupIDsInOrder = new ArrayList<String>();
	private static Map<String, QuestVisualInformation> identifierToVisualInformationMap = new HashMap<String, QuestVisualInformation>();
	private static QuestStatusDisplay display;
	private static boolean hasPlayerQuest = false;

	// FIXME choose our mfhc network wrapper
	private static SimpleNetworkWrapper networkWrapper = MHFCRegQuests.networkWrapper;

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
	 * @return
	 */
	public static QuestVisualInformation getVisualInformation(String identifier) {
		if (identifierToVisualInformationMap.containsKey(identifier)) {
			return identifierToVisualInformationMap.get(identifier);
		}
		networkWrapper.sendToServer(new MessageRequestQuestVisual(identifier));
		return QuestVisualInformation.LOADING_REPLACEMENT;
	}

	public static boolean hasPlayerQuest() {
		return hasPlayerQuest;
	}

	public static void init() {
		display = new QuestStatusDisplay();
		networkWrapper.registerMessage(QuestVisualInitHandler.class,
				MessageQuestScreenInit.class, discriminator_initMessage,
				Side.CLIENT);
		networkWrapper.registerMessage(QuestScreenVisualHandler.class,
				MessageQuestVisual.class, discriminator_visualMessage,
				Side.CLIENT);
		MinecraftForge.EVENT_BUS.register(display);

	}
}
