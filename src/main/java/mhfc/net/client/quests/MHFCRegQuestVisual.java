package mhfc.net.client.quests;

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
import mhfc.net.common.network.NetworkTracker;
import mhfc.net.common.network.packet.MessageQuestInit;
import mhfc.net.common.quests.api.QuestDefinition;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.util.services.IServiceAccess;
import mhfc.net.common.util.services.IServiceHandle;
import mhfc.net.common.util.services.IServicePhaseHandle;
import mhfc.net.common.util.services.Services;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class MHFCRegQuestVisual {
	public static void staticInit() {}

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

	private static final IServiceAccess<MHFCRegQuestVisual> serviceAccess = Services.instance
			.registerService("quest visuals", new IServiceHandle<MHFCRegQuestVisual>() {
				@Override
				public void startup(MHFCRegQuestVisual instance) {
					instance.initialize();
				};

				@Override
				public void shutdown(MHFCRegQuestVisual instance) {
					instance.shutdown();
				};
			}, MHFCRegQuestVisual::new);
	static {
		serviceAccess.addTo(NetworkTracker.clientConnectedPhase, IServicePhaseHandle.noInit());
	}

	public static MHFCRegQuestVisual getService() {
		return serviceAccess.getService();
	}

	public static void setPlayerVisual(Optional<IMissionInformation> visual) {
		getService().setVisual(visual);
	}

	public static void modifyRunningQuestList(String identifier, IMissionInformation visual) {
		getService().modMissionList(identifier, visual);
	}

	public static Set<String> getIdentifierList(String groupId) {
		return getService().getQuestIdentifiers(groupId);
	}

	public static Set<String> getRunningQuestIDs() {
		return getService().missionIDs;
	}

	/**
	 *
	 * @param identifier
	 *            of the quest in which you are interested
	 * @return Either the visual representation of the requested quest or a replacement <br>
	 *         representing loading.
	 */
	public static IVisualDefinition getVisualInformation(String identifier) {
		MHFCRegQuestVisual service = getService();
		QuestDefinition staticDescription = service.clientDataObject.getQuestDescription(identifier);
		if (staticDescription != null) {
			return staticDescription.getVisualInformation();
		}

		service.identifierToVisualInformationMap.put(identifier, DefaultQuestVisualDefinition.IDENTIFIER_ERROR);
		return DefaultQuestVisualDefinition.IDENTIFIER_ERROR;
	}

	public static IMissionInformation getMissionInformation(String identifier) {
		return getService().identifierToVisualInformationMap.get(identifier);
	}

	public static boolean hasPlayerQuest() {
		return getPlayerVisual().isPresent();
	}

	public static Optional<IMissionInformation> getPlayerVisual() {
		return getService().playersVisual;
	}

	private Set<String> missionIDs = new HashSet<>();
	private Map<String, IMissionInformation> identifierToVisualInformationMap = new HashMap<>();

	private QuestDescriptionRegistry clientDataObject = new QuestDescriptionRegistry();

	private Optional<IMissionInformation> playersVisual;

	private QuestStatusDisplay display = new QuestStatusDisplay();

	public void onInitializationMessage(MessageQuestInit message) {
		reset();
		message.initialize(clientDataObject);
	}

	public void setVisual(Optional<IMissionInformation> newVisual) {
		Objects.requireNonNull(newVisual);
		playersVisual.ifPresent(IMissionInformation::cleanUp);
		playersVisual = newVisual;
	}

	public void modMissionList(String identifier, IMissionInformation visual) {
		boolean clear = visual == null;
		if (clear) {
			identifierToVisualInformationMap.remove(identifier);
			missionIDs.remove(identifier);
			GuiQuestBoard.questBoard.addQuest(identifier, visual);
		} else {
			identifierToVisualInformationMap.put(identifier, visual);
			missionIDs.add(identifier);
			GuiQuestBoard.questBoard.removeQuest(identifier);
		}
	}

	public Set<String> getQuestIdentifiers(String group) {
		return clientDataObject.getQuestIdentifiersFor(group);
	}

	protected void logStats() {
		QuestDescriptionRegistry dataObject = clientDataObject;
		int numberQuests = dataObject.getFullQuestDescriptionMap().size();
		int numberGroups = dataObject.getGroupsInOrder().size();
		String output = String.format("Loaded %d quests in %d groups.", numberQuests, numberGroups);
		MHFCMain.logger().debug(output);
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
		this.identifierToVisualInformationMap.clear();
		this.clientDataObject.clearData();
		this.setVisual(Optional.empty());
	}

}
