package mhfc.net.common.quests;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import mhfc.net.MHFCMain;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.exploration.ExplorationAdapter;
import net.minecraft.entity.player.EntityPlayerMP;

public class QuestExplorationManager extends ExplorationAdapter {

	QuestFlair flair;
	IActiveArea initialInstance;
	Mission quest;

	public QuestExplorationManager(QuestFlair flair, IActiveArea initialInstance, Mission quest) {
		this.flair = Objects.requireNonNull(flair);
		this.initialInstance = Objects.requireNonNull(initialInstance);
		this.quest = quest;
	}

	@Override
	protected QuestFlair getFlairFor(IAreaType type) {
		return flair;
	}

	@Override
	protected void transferIntoInstance(EntityPlayerMP player, IAreaType type, Consumer<IActiveArea> callback) {
		Optional<IActiveArea> activeAreaOption = getAreasOfType(type).stream().findFirst();
		if (activeAreaOption.isPresent()) {
			MHFCMain.logger().debug("Transfering player into existing quest area instance");
			IActiveArea area = activeAreaOption.get();
			transferIntoInstance(player, area);
			callback.accept(area);
		} else {
			MHFCMain.logger().debug("Transfering player into new quest area instance");
			transferIntoNewInstance(player, type, callback);
		}
	}

	@Override
	protected void respawnWithoutInstance(EntityPlayerMP player) throws UnsupportedOperationException {
		throw new UnsupportedOperationException(
				"Quest had to respawn a player without an available instance. This is a bug and should never be called");
	}

	@Override
	protected void respawnInInstance(EntityPlayerMP player, IActiveArea instance) {
		AreaTeleportation.movePlayerToArea(player, instance.getArea());
	}

	@Override
	public void respawn(EntityPlayerMP player) throws IllegalArgumentException {
		if (!quest.getPlayers().contains(player))
			throw new IllegalArgumentException("Only players on the quest can be managed by this manager");
		respawnInInstance(player, initialInstance);
		quest.updatePlayerEntity(player);
	}

	@Override
	protected IAreaType initialAreaType(EntityPlayerMP player) {
		throw new UnsupportedOperationException(
				"Quest had to respawn a player without an available instance. This is a bug and should never be called");
	}

	@Override
	public void onPlayerRemove(EntityPlayerMP player) {
		super.onPlayerRemove(player);
		quest.quitPlayer(player);
	}

	@Override
	public void initialAddPlayer(EntityPlayerMP player) throws IllegalArgumentException {
		throw new UnsupportedOperationException(
				"Quest manager can not be saved and not be the first to handle a spawn. This is a bug and should never be called");
	}

}
