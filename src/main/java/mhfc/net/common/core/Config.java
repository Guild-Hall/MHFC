package mhfc.net.common.core;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Heltrato
 *
 *
 *         NOTE: THERE WILL BE A REWORK FOR THIS CONFIGURATION SYSTEM IN WHICH WILL OCCUR ON UPDATING TO 1.12 AND LATEST
 *         VERSION
 *
 *         MHF Configuration Files Created for the fix of ID Conflicts in 1.6.4 however this wouldnt be last as 1.7 will
 *         going be release for Forge and MCP .
 *
 *         DN: public<? for easy access; DN: int<? no dec. variables;
 *
 */
public class Config {

	private boolean allCanPickup;
	public static int dimensionHandlerID;
	//	public final boolean setupComplexGraphics = false;   // I'll add this soon if dan is finish with the block HD's better biome performance for faster pc

	private Map<String, EnableSetting> enableSettings;
	private Map<String, ReloadTimeSetting> reloadTimeSettings;
	private Configuration config;

	// FIXME: rework the registry, current state is not maintainable,
	// externalize strings
	public Config(FMLPreInitializationEvent e) {
		enableSettings = new LinkedHashMap<>();
		reloadTimeSettings = new LinkedHashMap<>();
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


	public boolean isEnabled(String weapon) {
		EnableSetting es = enableSettings.get(weapon);
		return es == null || es.enabled;
	}


	public boolean isAllCanPickup() {
		return allCanPickup;
	}


	public int getDimensionHandlerID() {
		return dimensionHandlerID;
	}

}
