package mhfc.net.common.world.exploration;

import java.util.function.Consumer;

import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.entity.player.EntityPlayerMP;

public class OverworldManager extends ExplorationAdapter {

	public static OverworldManager instance = new OverworldManager();

	private OverworldManager() {}

	private void transferPlayerToOverworld(EntityPlayerMP player) {
		if (player.worldObj.provider.dimensionId == 0) {
			return;
		}
		AreaTeleportation.movePlayerToOverworld(player);
	}

	@Override
	public void transferIntoInstance(EntityPlayerMP player, IAreaType type, Consumer<IActiveArea> callback) {
		if (type == null) {
			playerToArea.put(player, null);
			transferPlayerToOverworld(player);
			callback.accept(null);
		} else {
			MHFCExplorationRegistry.bindPlayer(MHFCExploration.instance, player);
			MHFCExplorationRegistry.transferPlayer(player, type, callback);
		}
	}

	@Override
	protected QuestFlair getFlairFor(IAreaType type) {
		return QuestFlair.DAYTIME;
	}

	@Override
	protected void respawnWithoutInstance(EntityPlayerMP player) {
		// transferPlayerToOverworld(player);
	}

	@Override
	protected void respawnInInstance(EntityPlayerMP player, IActiveArea instance) {
		MHFCExplorationRegistry.bindPlayer(MHFCExploration.instance, player);
		MHFCExplorationRegistry.transferPlayer(player, instance.getType(), (t) -> {});
	}

	@Override
	protected IAreaType initialAreaType(EntityPlayerMP player) {
		return null;
	}

	@Override
	public void onPlayerAdded(EntityPlayerMP player) {
		super.onPlayerAdded(player);
		transferPlayerToOverworld(player);
	}
}
