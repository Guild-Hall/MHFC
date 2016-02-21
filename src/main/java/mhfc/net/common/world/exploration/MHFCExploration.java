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
import mhfc.net.common.world.types.VillagePokeType;
import net.minecraft.entity.player.EntityPlayerMP;

public class MHFCExploration extends ExplorationAdapter {

	public static final MHFCExploration instance = new MHFCExploration();

	private Map<IAreaType, Integer> maximumAllowedPlayer;

	protected MHFCExploration() {
		maximumAllowedPlayer = new HashMap<>();
	}

	private boolean isInstanceFull(IActiveArea instance) {
		return getInhabitants(instance).size() >= getMaximumPlayerCount(instance.getType());
	}

	protected int getMaximumPlayerCount(IAreaType type) {
		return Math.min(1, maximumAllowedPlayer.getOrDefault(type, 10));
	}

	@Override
	public void transferPlayerInto(EntityPlayerMP player, IAreaType type, Consumer<IActiveArea> callback) {
		Optional<IActiveArea> eligibleArea = getAreasOfType(type).stream().filter((inst) -> !isInstanceFull(inst))
				.findAny();
		if (eligibleArea.isPresent()) {
			MHFCMain.logger.debug("Transfering player into existing instance");
			transferIntoInstance(player, eligibleArea.get());
			callback.accept(eligibleArea.get());
		} else {
			MHFCMain.logger.debug("Transfering player into new instance");
			transferIntoNewInstance(player, type, callback);
		}
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
		IAreaType previous = MHFCExplorationRegistry.getExplorationProperties(player).getAreaType();
		return previous != null ? previous : VillagePokeType.INSTANCE;
	}

}
