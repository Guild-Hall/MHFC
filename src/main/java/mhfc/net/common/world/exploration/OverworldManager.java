package mhfc.net.common.world.exploration;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class OverworldManager implements IExplorationManager {
	private static final int OVERWORLD_DIMENSION = 0;

	protected final GameProfile playerProfile;

	public OverworldManager(GameProfile player) {
		this.playerProfile = Objects.requireNonNull(player);
	}

	protected EntityPlayerMP getPlayer() {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList()
				.getPlayerByUUID(playerProfile.getId());
	}

	private void transferPlayerToOverworld() {
		transferPlayerToOverworld(null);
	}

	private void transferPlayerToOverworld(JsonElement saveData) {
		EntityPlayerMP player = getPlayer();
		if (player.getEntityWorld().provider.getDimension() == OVERWORLD_DIMENSION) {
			if (saveData != null) {
				JsonObject save = saveData.getAsJsonObject();
				BlockPos pos = new BlockPos(
						save.get("x").getAsDouble(),
						save.get("y").getAsDouble(),
						save.get("z").getAsDouble());
				AreaTeleportation.moveEntityTo(player, pos);
			}
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
		transferPlayerToOverworld();
	}

	@Override
	public void onPlayerRemove() {}

	@Override
	public void respawn(JsonElement saveData) {
		transferPlayerToOverworld(saveData);
	}

	@Override
	public CompletionStage<IActiveArea> transferPlayerInto(IAreaType type, QuestFlair flair) {
		if (type == null) {
			transferPlayerToOverworld();
			return CompletableFuture.completedFuture(null);
		}
		MHFCExplorationRegistry.bindPlayer(new MHFCExploration(playerProfile), playerProfile);
		return MHFCExplorationRegistry.transferPlayer(playerProfile, type, flair);
	}

	@Override
	public JsonElement saveState() {
		EntityPlayerMP player = getPlayer();
		JsonObject saveObject = new JsonObject();
		saveObject.addProperty("x", player.posX);
		saveObject.addProperty("y", player.posY);
		saveObject.addProperty("z", player.posZ);
		return saveObject;
	}

}
