package mhfc.net.common.quests;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.exploration.ExplorationAdapter;
import net.minecraft.entity.player.EntityPlayerMP;

public class QuestExplorationManager extends ExplorationAdapter {

	private IActiveArea initialInstance;
	private Mission quest;
	private final Map<IAreaType, List<IActiveArea>> areaInstances = new HashMap<>();
	private final Map<IActiveArea, Set<EntityPlayerMP>> inhabitants = new IdentityHashMap<>();

	public QuestExplorationManager(
			EntityPlayerMP player,
			QuestFlair flair,
			IActiveArea initialInstance,
			Mission quest) {
		super(player);
		this.initialInstance = Objects.requireNonNull(initialInstance);
		this.quest = quest;
	}

	@Override
	protected Map<IActiveArea, Set<EntityPlayerMP>> getInhabitants() {
		return inhabitants;
	}

	@Override
	protected Map<IAreaType, List<IActiveArea>> getManagedInstances() {
		return areaInstances;
	}

	@Override
	protected void respawnWithoutInstance() throws UnsupportedOperationException {
		throw new UnsupportedOperationException(
				"Quest had to respawn a player without an available instance. This is a bug and should never be called");
	}

	@Override
	protected void respawnInInstance(IActiveArea instance) {
		AreaTeleportation.movePlayerToArea(player, instance.getArea());
	}

	@Override
	public void respawn() {
		if (!quest.getPlayers().contains(player)) {
			throw new IllegalArgumentException("Only players on the quest can be managed by this manager");
		}
		respawnInInstance(initialInstance);
		quest.updatePlayerEntity(player);
	}

	@Override
	protected IAreaType initialAreaType() {
		throw new UnsupportedOperationException(
				"Quest had to respawn a player without an available instance. This is a bug and should never be called");
	}

	@Override
	protected QuestFlair initialFlair() {
		throw new UnsupportedOperationException(
				"Quest had to respawn a player without an available instance. This is a bug and should never be called");
	}

	@Override
	public void onPlayerRemove() {
		super.onPlayerRemove();
		quest.quitPlayer(player);
	}

	@Override
	public void onPlayerJoined() {
		throw new UnsupportedOperationException(
				"Quest manager can not be saved and not be the first to handle a spawn. This is a bug and should never be called");
	}

}
