package mhfc.net.common.world.exploration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.entity.player.EntityPlayerMP;

public class OverworldManager implements IExplorationManager {

	public static OverworldManager instance = new OverworldManager();

	private OverworldManager() {}

	private void transferPlayerToOverworld(EntityPlayerMP player) {
		if (player.getEntityWorld().provider.getDimension() == 0) {
			return;
		}
		AreaTeleportation.movePlayerToOverworld(player.getEntityWorld().getMinecraftServer(), player);
	}

	@Override
	public IActiveArea getActiveAreaOf(EntityPlayerMP player) {
		return null;
	}

	@Override
	public void onPlayerJoined(EntityPlayerMP player) throws IllegalArgumentException {}

	@Override
	public void onPlayerAdded(EntityPlayerMP player) {
		transferPlayerToOverworld(player);
	}

	@Override
	public void onPlayerRemove(EntityPlayerMP player) {}

	@Override
	public void respawn(EntityPlayerMP player) throws IllegalArgumentException {
		transferPlayerToOverworld(player);
	}

	@Override
	public CompletionStage<IActiveArea> transferPlayerInto(EntityPlayerMP player, IAreaType type) {
		if (type == null) {
			transferPlayerToOverworld(player);
			return CompletableFuture.completedFuture(null);
		} else {
			MHFCExplorationRegistry.bindPlayer(MHFCExploration.instance, player);
			return MHFCExplorationRegistry.transferPlayer(player, type);
		}
	}

}
