package mhfc.net.common.world.exploration;

import java.util.function.Consumer;

import mhfc.net.MHFCMain;
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
		MHFCMain.logger.debug("Transfering player to overworld if needed");
		if (player.worldObj.provider.dimensionId == 0) {
			MHFCMain.logger.debug("Player already in overworld, nothing to do");
			return;
		}
		MHFCMain.logger.debug("Player is not in overworld, teleporting him");
		AreaTeleportation.movePlayerToOverworld(player);
	}

	@Override
	public void transferPlayerInto(EntityPlayerMP player, IAreaType type, Consumer<IActiveArea> callback) {
		if (type == null) {
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
		AreaTeleportation.movePlayerToOverworld(player);
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

}
