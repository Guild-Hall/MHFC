package mhfc.net.common.world.exploration;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.types.VillagePokeType;
import net.minecraft.entity.player.EntityPlayerMP;

public class MHFCExploration extends ExplorationAdapter {
	private static final Map<IAreaType, List<IActiveArea>> AREA_INSTANCES = new HashMap<>();
	private static final Map<IActiveArea, Set<EntityPlayerMP>> INHABITANTS = new IdentityHashMap<>();

	private static final Map<EntityPlayerMP, IAreaType> LAST_VISITED_AREA = new HashMap<>();
	private static final Map<EntityPlayerMP, QuestFlair> LAST_FLAIR = new HashMap<>();

	private static final Map<IAreaType, Integer> maximumAllowedPlayer = new HashMap<>();

	public MHFCExploration(EntityPlayerMP player) {
		super(player);
	}

	@Override
	protected Map<IActiveArea, Set<EntityPlayerMP>> getInhabitants() {
		return INHABITANTS;
	}

	@Override
	protected Map<IAreaType, List<IActiveArea>> getManagedInstances() {
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
	protected void transferIntoExistingInstance(IActiveArea area, QuestFlair flair) {
		super.transferIntoExistingInstance(area, flair);
		LAST_VISITED_AREA.put(player, area.getType());
		LAST_FLAIR.put(player, flair);
	}

	@Override
	protected void respawnWithoutInstance() {
		MHFCExplorationRegistry.releasePlayer(player);
	}

	@Override
	protected void respawnInInstance(IActiveArea instance) {
		AreaTeleportation.movePlayerToArea(player, instance.getArea());
	}

	@Override
	protected IAreaType initialAreaType() {
		IAreaType previous = LAST_VISITED_AREA.get(player);
		previous = previous != null ? previous : getTargetAreaOf();
		return previous != null ? previous : VillagePokeType.INSTANCE;
	}

	@Override
	protected QuestFlair initialFlair() {
		return QuestFlair.DAYTIME;
	}

	@Override
	public void onPlayerJoined() throws IllegalArgumentException {
		super.onPlayerJoined();
		respawn();
	}

}
