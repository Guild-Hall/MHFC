package mhfc.net.common.world.exploration;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class OverworldManager implements IExplorationManager {
	private static final int OVERWORLD_DIMENSION = 0;

	private EntityPlayerMP player;

	public OverworldManager(EntityPlayerMP player) {
		this.player = Objects.requireNonNull(player);
	}

	private void transferPlayerToOverworld(EntityPlayerMP player) {
		transferPlayerToOverworld(player, null);
	}

	private void transferPlayerToOverworld(EntityPlayerMP player, JsonElement saveData) {
		if (player.getEntityWorld().provider.getDimension() == OVERWORLD_DIMENSION) {
			return;
		}
		MinecraftServer server = player.getEntityWorld().getMinecraftServer();
		if (saveData == null) {
			AreaTeleportation.movePlayerToOverworld(server, player);
		} else {
			JsonObject save = saveData.getAsJsonObject();
			BlockPos pos = new BlockPos(
					save.get("x").getAsDouble(),
					save.get("y").getAsDouble(),
					save.get("z").getAsDouble());
			AreaTeleportation.movePlayerToOverworld(server, player, pos);
		}
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
	public void respawn(JsonElement saveData) {
		transferPlayerToOverworld(player, saveData);
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

	@Override
	public JsonElement saveState() {
		JsonObject saveObject = new JsonObject();
		saveObject.addProperty("x", player.posX);
		saveObject.addProperty("y", player.posY);
		saveObject.addProperty("z", player.posZ);
		return saveObject;
	}

}
