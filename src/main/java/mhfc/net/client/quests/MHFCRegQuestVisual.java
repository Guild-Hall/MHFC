package mhfc.net.client.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.client.gui.quests.GuiQuestBoard;
import mhfc.net.client.gui.quests.GuiQuestGiver;
import mhfc.net.client.gui.quests.GuiQuestJoin;
import mhfc.net.client.gui.quests.GuiQuestNew;
import mhfc.net.client.gui.quests.QuestStatusDisplay;
import mhfc.net.common.core.data.QuestDescriptionRegistryData;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.MessageQuestInit;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.network.packet.MessageRequestQuestVisual;
import mhfc.net.common.quests.IVisualInformation;
import mhfc.net.common.quests.QuestRunningInformation;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.api.QuestDescription;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class MHFCRegQuestVisual {
	public static class QuestScreenVisualHandler implements IMessageHandler<MessageQuestVisual, IMessage> {

		@Override
		public IMessage onMessage(MessageQuestVisual message, MessageContext ctx) {
			IVisualInformation visual = getInformationFromMessage(message);
			switch (message.getMessageType()) {
			case PERSONAL_QUEST:
				setPlayerVisual((QuestRunningInformation) visual, message);
				break;
			case RUNNING_QUEST:
				modifyRunningQuestList(visual, message);
				break;
			}
			return null;
		}
	}

	public static class QuestClientInitHandler implements IMessageHandler<MessageQuestInit, IMessage> {
		@Override
		public IMessage onMessage(MessageQuestInit message, MessageContext ctx) {
			clientDataObject = message.getQuestDescriptionData();
			MHFCRegQuestVisual.logStats(clientDataObject);
			return null;
		}
	}

	private static IVisualInformation getInformationFromMessage(MessageQuestVisual message) {
		return message.getInformation();
	}

	private static void setPlayerVisual(QuestRunningInformation visual, MessageQuestVisual message) {
		hasPlayerQuest = (visual != null);
		if (playersVisual != null) {
			playersVisual.cleanUp();
		}
		playerQuestIdentifier = message.getMessageIdentifier();
		playersVisual = visual;
	}

	private static void modifyRunningQuestList(IVisualInformation visual, MessageQuestVisual message) {
		boolean clear = visual == null;
		String identifier = message.getMessageIdentifier();
		if (clear) {
			identifierToVisualInformationMap.remove(identifier);
			runningQuestIDs.remove(identifier);
			questBoard.removeQuest(identifier);
		} else {
			identifierToVisualInformationMap.put(identifier, visual);
			runningQuestIDs.add(identifier);
			questBoard.addQuest(identifier, visual);
		}
	}

	private static void logStats(QuestDescriptionRegistryData dataObject) {
		int numberQuests = dataObject.getFullQuestDescriptionMap().size();
		int numberGroups = dataObject.getGroupsInOrder().size();
		String output = String.format("Loaded %d quests in %d groups.", numberQuests, numberGroups);
		MHFCMain.logger().debug(output);
	}

	public static final ResourceLocation QUEST_STATUS_INVENTORY_BACKGROUND = new ResourceLocation(
			MHFCReference.gui_status_inventory_tex);
	public static final ResourceLocation QUEST_STATUS_ONSCREEN_BACKGROUND = new ResourceLocation(
			MHFCReference.gui_status_onscreen_tex);
	public static final ResourceLocation QUEST_BOARD_BACKGROUND = new ResourceLocation(MHFCReference.gui_board_tex);
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
	public static final ResourceLocation CLICKABLE_LIST = new ResourceLocation(MHFCReference.gui_list_tex);

	private static Set<String> runningQuestIDs = new HashSet<String>();
	private static Map<String, IVisualInformation> identifierToVisualInformationMap = new HashMap<String, IVisualInformation>();

	private static QuestDescriptionRegistryData clientDataObject = new QuestDescriptionRegistryData();

	private static boolean hasPlayerQuest = false;
	@SuppressWarnings("unused")
	private static String playerQuestIdentifier;
	private static QuestRunningInformation playersVisual;

	@SideOnly(Side.CLIENT)
	private static QuestStatusDisplay display;

	@SideOnly(Side.CLIENT)
	private static GuiQuestJoin questBoard = new GuiQuestJoin(Minecraft.getMinecraft().thePlayer);

	public static GuiQuestGiver getScreen(int i, EntityPlayer playerEntity) {
		// ignore i for now

		List<String> list = new ArrayList<String>(MHFCQuestBuildRegistry.getGroupList());
		GuiQuestNew newQuest = new GuiQuestNew(list, playerEntity);
		return new GuiQuestGiver(playerEntity, newQuest);
	}

	public static Set<String> getIdentifierList(String groupId) {
		return clientDataObject.getQuestIdentifiersFor(groupId);
	}

	public static Set<String> getRunningQuestIDs() {
		return runningQuestIDs;
	}

	/**
	 *
	 * @param identifier
	 *            of the quest in which you are interested
	 * @return Either the visual representation of the requested quest or a replacement <br>
	 *         representing loading.
	 */
	public static IVisualInformation getVisualInformation(String identifier) {
		QuestDescription staticDescription = clientDataObject.getQuestDescription(identifier);
		if (staticDescription != null) {
			return staticDescription.getVisualInformation();
		}
		PacketPipeline.networkPipe.sendToServer(new MessageRequestQuestVisual(identifier));
		identifierToVisualInformationMap.put(identifier, QuestVisualInformation.LOADING_REPLACEMENT);
		return QuestVisualInformation.LOADING_REPLACEMENT;
	}

	public static IVisualInformation getQuestVisualInformation(String identifier) {
		return identifierToVisualInformationMap.get(identifier);
	}

	public static boolean hasPlayerQuest() {
		return hasPlayerQuest;
	}

	public static void init() {
		display = new QuestStatusDisplay();
		FMLCommonHandler.instance().bus().register(new QuestClientInitHandler());
		MinecraftForge.EVENT_BUS.register(display);
	}

	public static QuestRunningInformation getPlayerVisual() {
		return playersVisual;
	}

	public static GuiQuestBoard getQuestBoard(EntityPlayer player) {
		return new GuiQuestBoard(questBoard, player);
	}
}
