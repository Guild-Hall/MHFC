package mhfc.net.client.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mhfc.net.MHFCMain;
import mhfc.net.client.gui.quests.GuiQuestBoard;
import mhfc.net.client.gui.quests.GuiQuestGiver;
import mhfc.net.client.gui.quests.QuestStatusDisplay;
import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageQuestRunningSubscription;
import mhfc.net.common.network.packet.MessageQuestScreenInit;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.network.packet.MessageRequestQuestVisual;
import mhfc.net.common.quests.QuestRunningInformation;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;
import mhfc.net.common.util.lib.MHFCReference;
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
			MHFCMain.logger.info("Client received quest info");
			return null;
		}
	}

	// TODO rework this, it is so horrifying
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
						case MHFCQuestsRegistry.QUEST_TYPE_KILLING :
							realType = QuestType.Killing;
							break;
						case MHFCQuestsRegistry.QUEST_TYPE_HUNTING :
							realType = QuestType.Hunting;
							break;
						case MHFCQuestsRegistry.QUEST_TYPE_EPIC_HUNTING :
							realType = QuestType.EpicHunting;
							break;
						case MHFCQuestsRegistry.QUEST_TYPE_GATHERING :
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

	// FIXME change the texture
	public static final ResourceLocation QUEST_STATUS_INVENTORY_BACKGROUND = new ResourceLocation(
			MHFCReference.gui_status_inventory_tex);
	public static final ResourceLocation QUEST_STATUS_ONSCREEN_BACKGROUND = new ResourceLocation(
			MHFCReference.gui_status_onscreen_tex);
	public static final ResourceLocation QUEST_BOARD_BACKGROUND = new ResourceLocation(
			MHFCReference.gui_board_tex);
	public static final ResourceLocation HUNTER_BENCH_BURN_BACK = new ResourceLocation(
			MHFCReference.gui_hunterbench_burn_back_tex);
	public static final ResourceLocation HUNTER_BENCH_BURN_FRONT = new ResourceLocation(
			MHFCReference.gui_hunterbench_burn_front_tex);
	public static final ResourceLocation HUNTER_BENCH_BURN_TARGET = new ResourceLocation(
			MHFCReference.gui_hunterbench_burn_target_tex);
	public static final ResourceLocation QUEST_HUNTERBENCH_BACKGROUND = new ResourceLocation(
			MHFCReference.gui_hunterbench_back_tex);
	public static final ResourceLocation HUNTER_BENCH_COMPLETE = new ResourceLocation(
			MHFCReference.gui_hunterbench_complete_tex);

	private static Map<String, List<String>> groupIDToListMap = new HashMap<String, List<String>>();
	private static List<String> groupIDsInOrder = new ArrayList<String>();
	private static Map<String, QuestVisualInformation> identifierToVisualInformationMap = new HashMap<String, QuestVisualInformation>();
	private static Map<String, QuestRunningInformation> identifierToVisualRunningMap = new HashMap<String, QuestRunningInformation>();
	private static QuestStatusDisplay display;
	private static GuiQuestBoard questBoard = new GuiQuestBoard(
			Minecraft.getMinecraft().thePlayer);
	private static boolean hasPlayerQuest = false;
	private static QuestRunningInformation playersVisual;

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
		PacketPipeline.networkPipe.sendToServer(new MessageRequestQuestVisual(
				identifier));
		identifierToVisualInformationMap.put(identifier,
				QuestVisualInformation.LOADING_REPLACEMENT);
		return QuestVisualInformation.LOADING_REPLACEMENT;
	}

	public static QuestRunningInformation getRunningInformation(
			String identifier) {
		return identifierToVisualRunningMap.get(identifier);
	}

	public static void setAndSendRunningListenStatus(boolean newStatus) {
		PacketPipeline.networkPipe
				.sendToServer(new MessageQuestRunningSubscription(newStatus));
		if (!newStatus) {
			questBoard.clearList();
		}
	}

	public static boolean hasPlayerQuest() {
		return hasPlayerQuest;
	}

	// TODO make this a static block and then do intialization
	public static void init() {
		display = new QuestStatusDisplay();
		MinecraftForge.EVENT_BUS.register(display);
	}

	public static QuestRunningInformation getPlayerVisual() {
		return playersVisual;
	}

	public static GuiQuestBoard getQuestBoard() {
		return questBoard;
	}
}
