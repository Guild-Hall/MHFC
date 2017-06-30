package mhfc.net.common.eventhandler;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.ResourceLocations;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ResourceInterface.main_modid)
public class LootTableInjector {

	private static final String RESOURCE_DOMAIN = ResourceInterface.main_resource_domain;
	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(LootTable.class, new LootTable.Serializer())
			.create();
	private static final Logger LOGGER = LogManager.getLogger();

	@SubscribeEvent
	public static void onLootTableLoaded(LootTableLoadEvent event) {
		ResourceLocation lootTableName = event.getName();
		if (lootTableName.getResourceDomain().equals(RESOURCE_DOMAIN)) {
			if (event.getTable() != null) {
				LOGGER.debug("Already have a loot table for {}", lootTableName);
				return;
			}
			LootTable lootTable = loadLootTable(lootTableName);
			if (lootTable != null) {
				event.setTable(lootTable);
			}
		}
	}

	private static LootTable loadLootTable(ResourceLocation lootTableName) {
		String resourceDomain = lootTableName.getResourceDomain();
		String resourceLocation = "/assets/loot_tables/" + lootTableName.getResourcePath();
		if (!resourceLocation.endsWith(".json")) {
			resourceLocation = resourceLocation + ".json";
		}
		ResourceLocation lootTableLocation = new ResourceLocation(resourceDomain, resourceLocation);
		try (
				InputStreamReader reader = new InputStreamReader(
						ResourceLocations.openEmbeddedResource(lootTableLocation))) {
			return GSON.fromJson(reader, LootTable.class);
		} catch (IOException e) {
			LOGGER.debug("Could not load loot table from " + lootTableName, e);
			return null;
		}
	}

}
