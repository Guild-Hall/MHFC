package mhfc.net.common.world.exploration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.types.areas.VillagePokeType;
import net.minecraft.entity.player.EntityPlayerMP;

public class MHFCExploration extends ExplorationAdapter {

	public static final MHFCExploration instance = new MHFCExploration();
	protected Map<EntityPlayerMP, IAreaType> lastVisitedInstance;

	private Map<IAreaType, Integer> maximumAllowedPlayer;

	protected MHFCExploration() {
		maximumAllowedPlayer = new HashMap<>();
		lastVisitedInstance = new HashMap<>();
	}

	private boolean isInstanceFull(IActiveArea instance) {
		return getInhabitants(instance).size() >= getMaximumPlayerCount(instance.getType());
	}

	@Override
	protected void transferIntoInstance(EntityPlayerMP player, IAreaType type, Consumer<IActiveArea> callback) {
		Optional<IActiveArea> eligibleArea = getAreasOfType(type).stream().filter((inst) -> !isInstanceFull(inst))
				.findAny();
		if (eligibleArea.isPresent()) {
			MHFCMain.logger().debug("Transfering player into existing instance");
			IActiveArea area = eligibleArea.get();
			transferIntoInstance(player, area);
			callback.accept(area);
		} else {
			MHFCMain.logger().debug("Transfering player into new instance");
			transferIntoNewInstance(player, type, callback);
		}
	}

	protected int getMaximumPlayerCount(IAreaType type) {
		return Math.min(1, maximumAllowedPlayer.getOrDefault(type, 10));
	}

	@Override
	protected void transferIntoInstance(EntityPlayerMP player, IActiveArea area) {
		super.transferIntoInstance(player, area);
		lastVisitedInstance.put(player, area.getType());
	}

	@Override
	protected QuestFlair getFlairFor(IAreaType type) {
		return QuestFlair.DAYTIME;
	}

	@Override
	protected void respawnWithoutInstance(EntityPlayerMP player) {
		MHFCExplorationRegistry.releasePlayer(player);
	}

	@Override
	protected void respawnInInstance(EntityPlayerMP player, IActiveArea instance) {
		AreaTeleportation.movePlayerToArea(player, instance.getArea());
	}

	@Override
	protected IAreaType initialAreaType(EntityPlayerMP player) {
		IAreaType previous = lastVisitedInstance.get(player);
		previous = previous != null ? previous : MHFCExplorationRegistry.getExplorationProperties(player).getAreaType();
		return previous != null ? previous : VillagePokeType.INSTANCE;
	}

	@Override
	public void initialAddPlayer(EntityPlayerMP player) throws IllegalArgumentException {
		super.initialAddPlayer(player);
		respawn(player);
	}

}
