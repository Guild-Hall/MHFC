package mhfc.net.client.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mhfc.net.client.gui.quests.QuestGiverScreen;
import mhfc.net.client.gui.quests.QuestStatusDisplay;
import mhfc.net.common.core.registry.MHFCRegQuests;
import mhfc.net.common.network.packet.MessageQuestScreenInit;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.network.packet.MessageRequestQuestVisual;
import mhfc.net.common.quests.QuestRunningInformation;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;
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
			System.out
					.println("[MHFC] Received new identifier configuration from server");
			Map<String, List<String>> identifierLists = message
					.getIdentifierListMap();
			List<String> identifiers = message.getIdentifiers();
			groupIDsInOrder = identifiers;
			groupIDToListMap = identifierLists;
			questGiverScreenlist.clear();
			for (String groupID : groupIDsInOrder) {
				questGiverScreenlist.add(new QuestGiverScreen(groupIDToListMap
						.get(groupID).toArray(new String[0])));
			}
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
					if (identifier != "") {
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

	// FIXME Fix to a non-random chosen discriminator
	public static final int discriminator_initMessage = 128;
	public static final int discriminator_visualMessage = 129;
	public static final ResourceLocation QUEST_STATUS_INVENTORY_BACKGROUND = new ResourceLocation(
			"textures/gui/demo_background.png");
	public static final ResourceLocation QUEST_STATUS_ONSCREEN_BACKGROUND = new ResourceLocation(
			"textures/gui/demo_background.png");

	private static List<QuestGiverScreen> questGiverScreenlist = new ArrayList<QuestGiverScreen>();
	private static Map<String, List<String>> groupIDToListMap = new HashMap<String, List<String>>();
	private static List<String> groupIDsInOrder = new ArrayList<String>();
	private static Map<String, QuestVisualInformation> identifierToVisualInformationMap = new HashMap<String, QuestVisualInformation>();
	private static QuestStatusDisplay display;

	// FIXME choose our mfhc network wrapper
	private static SimpleNetworkWrapper networkWrapper = MHFCRegQuests.networkWrapper;

	public static QuestGiverScreen getScreen(int i) {
		if (i < 0 || i >= questGiverScreenlist.size())
			return null;
		return questGiverScreenlist.get(i);
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
