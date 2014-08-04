package mhfc.net.client.gui.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mhfc.net.common.core.registry.MHFCRegQuests;
import mhfc.net.common.network.packet.MessageQuestScreenInit;
import mhfc.net.common.network.packet.MessageQuestVisual;
import mhfc.net.common.network.packet.MessageRequestQuestVisual;
import mhfc.net.common.quests.QuestVisualInformation;
import mhfc.net.common.quests.QuestVisualInformation.QuestType;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class QuestGiverScreen extends GuiScreen {
	public static class QuestScreenInitHandler
			implements
				IMessageHandler<MessageQuestScreenInit, IMessage> {

		@Override
		public IMessage onMessage(MessageQuestScreenInit message,
				MessageContext ctx) {
			Map<String, List<String>> identifierLists = message
					.getIdentifierLists();
			List<String> identifiers = message.getIdentifiers();
			groupIDsInOrder = identifiers;
			groupIDToListMap = identifierLists;
			return null;
		}
	}

	public static class QuestScreenVisualHandler
			implements
				IMessageHandler<MessageQuestVisual, IMessage> {

		@Override
		public IMessage onMessage(MessageQuestVisual message, MessageContext ctx) {
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
			identifierToVisualInformationMap.put(identifier,
					new QuestVisualInformation(name, description, client, aims,
							fails, areaNameID, timeLimitInS, reward, fee,
							maxPartySize, realType));
			return null;
		}
	}

	// FIXME Fix to a non-random chosen discriminator
	public static final int messageDiscriminator = 128;
	public static final int visualMessageDiscriminator = 129;

	private static List<QuestGiverScreen> list = new ArrayList<QuestGiverScreen>();
	private static Map<String, List<String>> groupIDToListMap = new HashMap<String, List<String>>();
	private static List<String> groupIDsInOrder = new ArrayList<String>();
	private static Map<String, QuestVisualInformation> identifierToVisualInformationMap = new HashMap<String, QuestVisualInformation>();

	// FIXME choose our mfhc network wrapper
	private static SimpleNetworkWrapper networkWrapper = new SimpleNetworkWrapper(
			"QuestGroups");

	static {
		networkWrapper
				.registerMessage(QuestScreenInitHandler.class,
						MessageQuestScreenInit.class, messageDiscriminator,
						Side.CLIENT);
		networkWrapper.registerMessage(QuestScreenVisualHandler.class,
				MessageQuestVisual.class, visualMessageDiscriminator,
				Side.CLIENT);
	}

	public static QuestGiverScreen getScreen(int i) {
		if (i < 0 || i >= list.size())
			return null;
		return list.get(i);
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

	private List<String> groupIDsDisplayed;

	protected QuestGiverScreen(String[] groupIDs) {
		groupIDsDisplayed = new ArrayList<String>();
		for (int i = 0; i < groupIDs.length; i++)
			groupIDsDisplayed.add(groupIDs[i]);
	}

	@Override
	public void drawScreen(int positionX, int positionY, float partialTick) {
		super.drawScreen(positionX, positionY, partialTick);
	}

	@Override
	public void onGuiClosed() {
		// TODO Auto-generated method stub
		super.onGuiClosed();
	}

}
