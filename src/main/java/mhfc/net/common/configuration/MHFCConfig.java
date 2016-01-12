package mhfc.net.common.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

/**
 * @author Heltrato
 *
 *         MHF Configuration Files Created for the fix of ID Conflicts in 1.6.4 however this wouldnt be last as 1.7 will
 *         going be release for Forge and MCP .
 *
 *         DN: public<? for easy access; DN: int<? no dec. variables;
 *
 */
public class MHFCConfig {

	private boolean allCanPickup;
	private boolean spawnTigrex;
	private boolean spawnKirin;
	private int dimensionHandlerID;
	//	public final boolean setupComplexGraphics = false;   // I'll add this soon if dan is finish with the block HD's better biome performance for faster pc

	private Map<String, EnableSetting> enableSettings;
	private Map<String, ReloadTimeSetting> reloadTimeSettings;
	private Configuration config;

	// FIXME: rework the registry, current state is not maintainable,
	// externalize strings
	public MHFCConfig(FMLPreInitializationEvent e) {
		enableSettings = new LinkedHashMap<String, EnableSetting>();
		reloadTimeSettings = new LinkedHashMap<String, ReloadTimeSetting>();
		config = new Configuration(e.getSuggestedConfigurationFile());

	}

	/**
	 * TBC after all Settings have been registered
	 */
	public void init() {
		for (EnableSetting es : enableSettings.values()) {
			es.enabled = config.get("enable", es.settingName, es.enabled).getBoolean(es.enabled);
		}
		for (ReloadTimeSetting rs : reloadTimeSettings.values()) {
			rs.reloadTime = config.get("reloadtime", rs.settingName, rs.reloadTime).getInt(rs.reloadTime);
		}

		spawnTigrex = config.get("MHFC Mobs", "Summon Tigrex", true).getBoolean(true);
		spawnKirin = config.get("MHFC Mobs", "Summon Kirin", true).getBoolean(true);
		allCanPickup = config.get("settings", "pickup-all", true, "Another work-in-progress soon.").getBoolean(true);
		dimensionHandlerID = config.getInt(
				"dimensionHandlerID",
				"Questing",
				-71,
				Integer.MIN_VALUE,
				Integer.MAX_VALUE,
				"Change this if you have any collisions");
		config.save();
	}

	private static abstract class Setting {
		final String settingName;

		Setting(String name) {
			settingName = name;
		}
	}

	private static class ReloadTimeSetting extends Setting {
		int reloadTime;

		ReloadTimeSetting(String name, int time) {
			super(name + ".reloadtime");
			reloadTime = time;
		}
	}

	private static class EnableSetting extends Setting {
		boolean enabled;

		EnableSetting(String name) {
			super(name + ".enabled");
			enabled = true;
		}

	}

	public void addEnableSetting(String weapon) {
		enableSettings.put(weapon, new EnableSetting(weapon));
	}

	public void addReloadTimeSetting(String weapon, int defaulttime) {
		reloadTimeSettings.put(weapon, new ReloadTimeSetting(weapon, defaulttime));
	}

	public boolean isEnabled(String weapon) {
		EnableSetting es = enableSettings.get(weapon);
		return es == null || es.enabled;
	}

	public int getReloadTime(String weapon) {
		ReloadTimeSetting rs = reloadTimeSettings.get(weapon);
		return rs == null ? 0 : rs.reloadTime;
	}

	public boolean isAllCanPickup() {
		return allCanPickup;
	}

	public boolean isSpawnTigrex() {
		return spawnTigrex;
	}

	public boolean isSpawnKirin() {
		return spawnKirin;
	}

	public int getDimensionHandlerID() {
		return dimensionHandlerID;
	}

}
