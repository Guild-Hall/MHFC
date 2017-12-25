package mhfc.net.common.world.exploration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.JsonElement;

import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.util.BiMultiMap;
import mhfc.net.common.util.HashBiMultiMap;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.entity.player.EntityPlayerMP;

public class MHFCExploration extends ExplorationAdapter {
	private static final Table<IAreaType, QuestFlair, Collection<IActiveArea>> AREA_INSTANCES = HashBasedTable.create();
	private static final BiMultiMap<IActiveArea, EntityPlayerMP> INHABITANTS = new HashBiMultiMap<>();

	private static final Map<EntityPlayerMP, IAreaType> LAST_VISITED_AREA = new HashMap<>();
	private static final Map<EntityPlayerMP, QuestFlair> LAST_FLAIR = new HashMap<>();

	private static final Map<IAreaType, Integer> maximumAllowedPlayer = new HashMap<>();

	public MHFCExploration(EntityPlayerMP player) {
		super(player);
	}

	@Override
	protected BiMultiMap<IActiveArea, EntityPlayerMP> getInhabitants() {
		return INHABITANTS;
	}

	@Override
	protected Table<IAreaType, QuestFlair, Collection<IActiveArea>> getManagedInstances() {
		return AREA_INSTANCES;
	}

	private boolean isInstanceFull(IActiveArea instance) {
		return getInhabitants(instance).size() >= getMaximumPlayerCount(instance.getType());
	}

	protected int getMaximumPlayerCount(IAreaType type) {
		return Math.min(1, maximumAllowedPlayer.getOrDefault(type, 10));
	}

	@Override
	protected boolean canTransferIntoArea(IActiveArea area) {
		return !isInstanceFull(area);
	}

	@Override
	protected void transferIntoExistingInstance(IActiveArea area) {
		super.transferIntoExistingInstance(area);
		LAST_VISITED_AREA.put(getPlayer(), area.getType());
		LAST_FLAIR.put(getPlayer(), area.getFlair());
	}

	@Override
	protected void respawnWithoutInstance() {
		MHFCExplorationRegistry.releasePlayer(getPlayer());
	}

	@Override
	protected void respawnInInstance(IActiveArea instance) {
		transferIntoExistingInstance(instance);
	}

	@Override
	public void onPlayerJoined() throws IllegalArgumentException {
		super.onPlayerJoined();
		respawn(null);
	}

	@Override
	public JsonElement saveState() {
		return null;
	}

	@Override
	protected void loadFromSaveData(JsonElement saveData) {}

}
