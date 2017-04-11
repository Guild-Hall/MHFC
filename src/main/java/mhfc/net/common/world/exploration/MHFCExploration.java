package mhfc.net.common.world.exploration;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import mhfc.net.MHFCMain;
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

	@Override
	protected CompletionStage<IActiveArea> transferInto(IAreaType type, QuestFlair flair) {
		Optional<IActiveArea> eligibleArea = getAreasOfType(type).stream().filter((inst) -> !isInstanceFull(inst))
				.findAny();
		if (eligibleArea.isPresent()) {
			MHFCMain.logger().debug("Transfering player into existing instance");
			IActiveArea area = eligibleArea.get();
			transferIntoExistingInstance(area, flair);
			return CompletableFuture.completedFuture(area);
		} else {
			MHFCMain.logger().debug("Transfering player into new instance");
			return transferIntoNewInstance(type, flair);
		}
	}

	protected int getMaximumPlayerCount(IAreaType type) {
		return Math.min(1, maximumAllowedPlayer.getOrDefault(type, 10));
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
