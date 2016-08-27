package mhfc.net.client.quests;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.client.gui.hud.QuestStatusDisplay;
import mhfc.net.client.gui.quests.GuiQuestBoard;
import mhfc.net.client.quests.api.IMissionInformation;
import mhfc.net.client.quests.api.IVisualDefinition;
import mhfc.net.common.core.data.QuestDescriptionRegistry;
import mhfc.net.common.core.registry.RegistryWrapper;
import mhfc.net.common.network.NetworkTracker;
import mhfc.net.common.network.message.quest.MessageQuestInit;
import mhfc.net.common.quests.api.QuestDefinition;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.util.services.IServiceKey;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class MHFCRegQuestVisual {
	public static void staticInit() {}

	public static final ResourceLocation QUEST_BOARD_BACKGROUND = new ResourceLocation(MHFCReference.gui_board_tex);
	public static final ResourceLocation CLICKABLE_LIST = new ResourceLocation(MHFCReference.gui_list_tex);

	private static final IServiceKey<MHFCRegQuestVisual> serviceAccess = RegistryWrapper.registerService(
			"quest visuals",
			MHFCRegQuestVisual::new,
			MHFCRegQuestVisual::shutdown,
			NetworkTracker.clientConnectedPhase);

	public static MHFCRegQuestVisual getService() {
		return serviceAccess.getService();
	}

	public static Set<String> getAvailableQuestIDs(String groupId) {
		return getService().getQuestIdentifiers(groupId);
	}

	public static Set<String> getRunningMissionIDs() {
		return Collections.unmodifiableSet(getService().getMissionIdentifiers());
	}

	/**
	 *
	 * @param questID
	 *            of the quest in which you are interested
	 * @return Either the visual representation of the requested quest or a replacement <br>
	 *         representing loading.
	 */
	public static IVisualDefinition getQuestInformation(String questID) {
		MHFCRegQuestVisual service = getService();
		QuestDefinition staticDescription = service.clientDataObject.getQuestDescription(questID);
		if (staticDescription != null) {
			return staticDescription.getVisualInformation();
		}
		return DefaultQuestVisualDefinition.IDENTIFIER_ERROR;
	}

	/**
	 * Gets the dynamic information for a running mission. If the mission is unavailable, it is lazily initialized with
	 * a loading replacement, and a refresh is polled from the server.
	 *
	 * @param missionID
	 *            the mission you are interested in.
	 * @return
	 */
	public static IMissionInformation getMissionInformation(String missionID) {
		return getService().missionVisuals.get(missionID);
	}

	public static boolean hasPlayerQuest() {
		return getPlayerVisual().isPresent();
	}

	public static Optional<IMissionInformation> getPlayerVisual() {
		return getService().playerVisual;
	}

	public static void startNewMission(String questID, String missionID) {
		getService().createMission(questID, missionID);
	}

	public static void joinMission(String missionID) {
		getService().setPlayerMission(missionID);
	}

	public static void departMission(String missionID) {
		getService().unsetPlayerMission(missionID);
	}

	public static void endMission(String missionID) {
		getService().destroyMission(missionID);
	}

	private Set<String> missionIDs = new HashSet<>();
	private Map<String, IMissionInformation> missionVisuals = new HashMap<>();

	private QuestDescriptionRegistry clientDataObject = new QuestDescriptionRegistry();
	private Optional<IMissionInformation> playerVisual = Optional.empty();
	private QuestStatusDisplay display = new QuestStatusDisplay();

	public MHFCRegQuestVisual() {
		initialize();
	}

	/**
	 * (Re-)initializes the registry with the quest received in the init message.
	 *
	 * @param message
	 */
	public void onInitializationMessage(MessageQuestInit message) {
		reset();
		message.initialize(clientDataObject);
	}

	public Set<String> getQuestIdentifiers(String group) {
		return Collections.unmodifiableSet(clientDataObject.getQuestIdentifiersFor(group));
	}

	public Set<String> getMissionIdentifiers() {
		return Collections.unmodifiableSet(missionIDs);
	}

	public void createMission(String questID, String missionID) {
		if (missionIDs.contains(missionID)) {
			return;
		}
		IMissionInformation info = clientDataObject.getQuestDescription(questID).getVisualInformation().build();
		missionIDs.add(missionID);
		missionVisuals.put(missionID, info);
		GuiQuestBoard.questBoard.addQuest(missionID, info);
	}

	public void destroyMission(String missionID) {
		if (!missionIDs.contains(missionID)) {
			return;
		}
		missionIDs.remove(missionID);
		missionVisuals.remove(missionID);
		GuiQuestBoard.questBoard.removeQuest(missionID);
	}

	public void setPlayerMission(String missionID) {
		IMissionInformation info = missionVisuals.get(missionID);
		setVisual(Optional.ofNullable(info));
	}

	public void unsetPlayerMission(String missionID) {
		// TODO: maybe check that the active mission actually has the missionID?
		setVisual(Optional.empty());
	}

	protected void logStats() {
		QuestDescriptionRegistry dataObject = clientDataObject;
		int numberQuests = dataObject.getFullQuestDescriptionMap().size();
		int numberGroups = dataObject.getGroupsInOrder().size();
		String output = String.format("Client loaded %d quests in %d groups.", numberQuests, numberGroups);
		MHFCMain.logger().debug(output);
	}

	private void setVisual(Optional<IMissionInformation> newVisual) {
		Objects.requireNonNull(newVisual);
		playerVisual.ifPresent(IMissionInformation::cleanUp);
		playerVisual = newVisual;
	}

	private void initialize() {
		reset();
		MinecraftForge.EVENT_BUS.register(display);
	}

	private void shutdown() {
		reset();
		MinecraftForge.EVENT_BUS.unregister(display);
	}

	private void reset() {
		this.missionIDs.clear();
		this.missionVisuals.clear();
		this.clientDataObject.clearData();
		this.setVisual(Optional.empty());
	}

}
