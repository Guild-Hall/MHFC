package mhfc.net.common.quests.api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.quests.spawns.NoSpawn;
import mhfc.net.common.util.io.DelegatedConvertible;
import net.minecraft.util.ResourceLocation;

public class SpawnInformationDelegate extends DelegatedConvertible<ISpawnInformation, ResourceLocation> {
	public static final SpawnInformationDelegate MISSING = new SpawnInformationDelegate(
			new ResourceLocation(MHFCQuestBuildRegistry.SPAWN_NONE_TYPE),
			NoSpawn.INSTANCE);

	public SpawnInformationDelegate(ResourceLocation typeKey, ISpawnInformation spawnInformation) {
		super(typeKey, spawnInformation);
	}

	/**
	 * If element is null, converts a JsonNull instead. If the result is null, returns {@link #MISSING}
	 *
	 * @param element
	 * @param context
	 * @return
	 */
	public static final SpawnInformationDelegate deserialize(JsonElement element, JsonDeserializationContext context) {
		if (element == null) {
			// Gson context.deserialize will return null when element is null, which is not what we want
			element = JsonNull.INSTANCE;
		}
		SpawnInformationDelegate spawnInformation = context.deserialize(element, SpawnInformationDelegate.class);
		return spawnInformation == null ? MISSING : spawnInformation;
	}
}
