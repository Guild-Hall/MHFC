package mhfc.net.common.world.exploration;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.entity.player.EntityPlayerMP;

public class OverworldManager implements IExplorationManager {
	private static final int OVERWORLD_DIMENSION = 0;

	private EntityPlayerMP player;

	public OverworldManager(EntityPlayerMP player) {
		this.player = Objects.requireNonNull(player);
	}

	private void transferPlayerToOverworld(EntityPlayerMP player) {
		if (player.getEntityWorld().provider.getDimension() == OVERWORLD_DIMENSION) {
			return;
		}
		AreaTeleportation.movePlayerToOverworld(player.getEntityWorld().getMinecraftServer(), player);
	}

	@Override
	public IActiveArea getActiveAreaOf() {
		return null;
	}

	@Override
	public void onPlayerJoined() throws IllegalArgumentException {}

	@Override
	public void onPlayerAdded() {
		transferPlayerToOverworld(player);
	}

	@Override
	public void onPlayerRemove() {}

	@Override
	public void respawn() {
		transferPlayerToOverworld(player);
	}

	@Override
	public CompletionStage<IActiveArea> transferPlayerInto(IAreaType type, QuestFlair flair) {
		if (type == null) {
			transferPlayerToOverworld(player);
			return CompletableFuture.completedFuture(null);
		} else {
			MHFCExplorationRegistry.bindPlayer(new MHFCExploration(player), player);
			return MHFCExplorationRegistry.transferPlayer(player, type, flair);
		}
	}

}
