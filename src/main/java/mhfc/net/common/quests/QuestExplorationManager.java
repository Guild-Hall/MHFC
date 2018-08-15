package mhfc.net.common.quests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfile;

import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.util.BiMultiMap;
import mhfc.net.common.util.HashBiMultiMap;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.exploration.ExplorationAdapter;

public class QuestExplorationManager extends ExplorationAdapter {

	public static void bindPlayersToMission(Collection<GameProfile> players, Mission mission) {
		IActiveArea questArea = mission.getQuestingArea();
		IAreaType questType = questArea.getType();
		QuestFlair questFlair = questArea.getFlair();

		Table<IAreaType, QuestFlair, Collection<IActiveArea>> sharedAreaInstances = HashBasedTable.create();
		sharedAreaInstances.put(questType, questFlair, new ArrayList<>(Collections.singleton(questArea)));
		questArea.engage(); // Engage "by hand" as it is pre-injected

		BiMultiMap<IActiveArea, GameProfile> sharedInhabitants = new HashBiMultiMap<>();
		sharedInhabitants.putAll(questArea, players);

		for (GameProfile player : players) {
			QuestExplorationManager manager = new QuestExplorationManager(
					player,
					questArea,
					mission,
					sharedAreaInstances,
					sharedInhabitants);
			MHFCExplorationRegistry.bindPlayer(manager, player);
		}
	}

	private Mission quest;
	private final Table<IAreaType, QuestFlair, Collection<IActiveArea>> areaInstances;
	private final BiMultiMap<IActiveArea, GameProfile> inhabitants;

	private QuestExplorationManager(
			GameProfile player,
			IActiveArea initialInstance,
			Mission quest,
			Table<IAreaType, QuestFlair, Collection<IActiveArea>> areaInstances,
			BiMultiMap<IActiveArea, GameProfile> sharedInhabitants) {
		super(player);
		Objects.requireNonNull(initialInstance);
		this.quest = Objects.requireNonNull(quest);
		this.areaInstances = areaInstances;
		this.inhabitants = sharedInhabitants;
	}

	@Override
	protected BiMultiMap<IActiveArea, GameProfile> getInhabitants() {
		return inhabitants;
	}

	@Override
	protected Table<IAreaType, QuestFlair, Collection<IActiveArea>> getManagedInstances() {
		return areaInstances;
	}

	@Override
	protected void respawnWithoutInstance() throws UnsupportedOperationException {
		throw new UnsupportedOperationException(
				"Quest had to respawn a player without an available instance. This is a bug and should never be called");
	}

	@Override
	protected void respawnInInstance(IActiveArea instance) {
		transferIntoExistingInstance(instance);
	}

	@Override
	public void respawn(JsonElement saveData) {
		if (!quest.getPlayers().contains(playerprofile)) {
			throw new IllegalArgumentException("Only players on the quest can be managed by this manager");
		}
		super.respawn(saveData);
	}

	@Override
	public void onPlayerRemove() {
		super.onPlayerRemove();
		quest.quitPlayer(getPlayer());
	}

	@Override
	public void onPlayerJoined() {
		throw new UnsupportedOperationException(
				"Quest manager can not be saved and not be the first to handle a spawn. This is a bug and should never be called");
	}

	@Override
	public CompletionStage<IActiveArea> transferPlayerInto(IAreaType type, QuestFlair flair) {
		CompletableFuture<IActiveArea> result = new CompletableFuture<>();
		result.completeExceptionally(
				new UnsupportedOperationException("Quest manager can not transfer players into other areas"));
		return result;
	}

	@Override
	public JsonElement saveState() {
		return null;
	}

	@Override
	protected void loadFromSaveData(JsonElement saveData) {}

}
