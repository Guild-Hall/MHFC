package mhfc.net.client.quests;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.client.gui.quests.*;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.network.packet.MessageRequestQuestVisual;
import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.quests.QuestRunningInformation;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;
import mhfc.net.common.quests.api.QuestDescription;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class MHFCRegQuestVisual {
	public static class QuestScreenVisualHandler
		implements
			IMessageHandler<MessageQuestVisual, IMessage> {

		@Override
		public IMessage onMessage(MessageQuestVisual message,
			MessageContext ctx) {
			QuestVisualInformation visual = getInformationFromMessage(message);
			switch (message.getTypeID()) {
				case 0 :
					modifyVisualOfIdentifier(visual, message);
					break;
				case 1 :
					setPlayerVisual(visual, message);
					break;
				case 2 :
					modifyRunningQuestList(visual, message);
			}
			return null;
		}
	}

	private static QuestVisualInformation getInformationFromMessage(
		MessageQuestVisual message) {
		String[] strings = message.getStrings();
		String name = strings[1];
		if ("".equals(name))
			return null;
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
			case MHFCQuestBuildRegistry.QUEST_TYPE_KILLING :
				realType = QuestType.Killing;
				break;
			case MHFCQuestBuildRegistry.QUEST_TYPE_HUNTING :
				realType = QuestType.Hunting;
				break;
			case MHFCQuestBuildRegistry.QUEST_TYPE_EPIC_HUNTING :
				realType = QuestType.EpicHunting;
				break;
			case MHFCQuestBuildRegistry.QUEST_TYPE_GATHERING :
				realType = QuestType.Gathering;
				break;
			default :
		}
		QuestVisualInformation visual;
		visual = new QuestVisualInformation(name, description, client, aims,
			fails, areaNameID, timeLimitInS, reward, fee, maxPartySize,
			realType);
		return visual;
	}

	private static void modifyVisualOfIdentifier(QuestVisualInformation visual,
		MessageQuestVisual message) {
		String identifier = message.getStrings()[0];
		identifierToVisualInformationMap.put(identifier, visual);
	}

	private static void setPlayerVisual(IVisualInformation visual,
		MessageQuestVisual message) {
		String[] strings = message.getStrings();
		hasPlayerQuest = (visual != null);
		QuestRunningInformation runInfo = new QuestRunningInformation(visual,
			strings[12], strings[13]);
		if (playersVisual != null) {
			playersVisual.cleanUp();
		}
		playersVisual = (!hasPlayerQuest) ? null : runInfo;
	}

	private static void modifyRunningQuestList(IVisualInformation visual,
		MessageQuestVisual message) {
		String[] strings = message.getStrings();
		String identifier = strings[0];
		boolean clear = visual == null;
		QuestRunningInformation runInfo = new QuestRunningInformation(visual,
			strings[12], strings[13]);
		if (clear) {
			identifierToVisualRunningMap.remove(identifier);
			runningQuestIDs.remove(identifier);
			questBoard.removeQuest(identifier);
		} else {
			identifierToVisualRunningMap.put(identifier, runInfo);
			runningQuestIDs.add(identifier);
			questBoard.addQuest(identifier, runInfo);
		}
	}

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
	public static final ResourceLocation HUNTER_BENCH_FUEL_DURATION = new ResourceLocation(
		MHFCReference.gui_hunterbench_fuel_tex);
	public static final ResourceLocation CLICKABLE_LIST = new ResourceLocation(
		MHFCReference.gui_list_tex);

	private static Set<String> runningQuestIDs = new HashSet<String>();
	private static Map<String, QuestVisualInformation> identifierToVisualInformationMap = new HashMap<String, QuestVisualInformation>();
	private static Map<String, QuestRunningInformation> identifierToVisualRunningMap = new HashMap<String, QuestRunningInformation>();

	@SideOnly(Side.CLIENT)
	private static QuestStatusDisplay display;

	@SideOnly(Side.CLIENT)
	private static GuiQuestJoin questBoard = new GuiQuestJoin(Minecraft
		.getMinecraft().thePlayer);

	private static boolean hasPlayerQuest = false;
	private static QuestRunningInformation playersVisual;

	public static GuiQuestGiver getScreen(int i, EntityPlayer playerEntity) {
		// ignore i for now

		List<String> list = new ArrayList<String>(MHFCQuestBuildRegistry
			.getGroupList());
		GuiQuestNew newQuest = new GuiQuestNew(list, playerEntity);
		return new GuiQuestGiver(playerEntity, newQuest);
	}

	public static Set<String> getIdentifierList(String groupId) {
		return MHFCQuestBuildRegistry.getQuestIdentifiersFor(groupId);
	}

	public static Set<String> getRunningQuestIDs() {
		return runningQuestIDs;
	}

	/**
	 *
	 * @param identifier
	 *            of the quest in which you are interested
	 * @return Either the visual representation of the requested quest or a
	 *         replacement <br>
	 *         representing loading.
	 */
	public static IVisualInformation getVisualInformation(
		String identifier) {
		QuestDescription staticDescription = MHFCQuestBuildRegistry
			.getQuestDescription(identifier);
		if (staticDescription != null) {
			return staticDescription.getVisualInformation();
		}
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

	public static GuiQuestBoard getQuestBoard(EntityPlayer player) {
		return new GuiQuestBoard(questBoard, player);
	}
}
