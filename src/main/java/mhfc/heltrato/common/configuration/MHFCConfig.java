package mhfc.heltrato.common.configuration;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;


/**
 * @author Heltrato
 * 
 * MHF Configuration Files Created for the fix of ID Conflicts in 1.6.4 however this wouldnt be last as 1.7 will
 * going be release for Forge and MCP .
 * 
 * DN: public<? for easy access;
 * DN: int<? no dec. variables;
 * 
 *
 */

public class MHFCConfig {
		
	public static boolean allCanPickup;
	public static boolean spawnTigrex;
	public static boolean spawnKirin;
	public static boolean setupComplexGraphics = false;// this initialize a improved game graphics for future use .
	private static Map<String, EnableSetting>		enableSettings;
	private static Map<String, ReloadTimeSetting>	reloadTimeSettings;
	
	public MHFCConfig(Configuration configuration){
		enableSettings = new LinkedHashMap<String, EnableSetting>();
		reloadTimeSettings = new LinkedHashMap<String, ReloadTimeSetting>();
	}
	
	public static void init(FMLPreInitializationEvent e){
		Configuration config = new Configuration(new File("config/MHFC.cfg"));
		

		spawnTigrex = config.get("MHFC Mobs", "Summon Tigrex", true).getBoolean(true);
		spawnKirin = config.get("MHFC Mobs", "Summon Kirin", true).getBoolean(true);
		
		allCanPickup = config.get("settings", "pickup-all", true, "Change this to 'false' to allow only the thrower/shooter of the projectile to pick the item up. If set to 'true' everyone can pick the item up.").getBoolean(true);
		for (EnableSetting es : enableSettings.values())
		{
			es.enabled = config.get("enable", es.settingName, es.enabled).getBoolean(es.enabled);
		}
		for (ReloadTimeSetting rs : reloadTimeSettings.values())
		{
			rs.reloadTime = config.get("reloadtime", rs.settingName, rs.reloadTime).getInt(rs.reloadTime);
		}
		config.save();
}
	
	private static abstract class Setting
	{
		final String	settingName;
		
		Setting(String name)
		{
			settingName = name;
		}
	}
	
	private static class ReloadTimeSetting extends Setting
	{
		int	reloadTime;
		
		ReloadTimeSetting(String name, int time)
		{
			super(name + ".reloadtime");
			reloadTime = time;
		}
	}
	
	private static class EnableSetting extends Setting
	{
		boolean	enabled;
		
		EnableSetting(String name)
		{
			super(name + ".enabled");
			enabled = true;
		}
		
	}
	
	public void addEnableSetting(String weapon)
	{
		enableSettings.put(weapon, new EnableSetting(weapon));
	}
	
	public void addReloadTimeSetting(String weapon, int defaulttime)
	{
		reloadTimeSettings.put(weapon, new ReloadTimeSetting(weapon, defaulttime));
	}
	
	public boolean isEnabled(String weapon)
	{
		EnableSetting es = enableSettings.get(weapon);
		return es == null || es.enabled;
	}
	
	public int getReloadTime(String weapon)
	{
		ReloadTimeSetting rs = reloadTimeSettings.get(weapon);
		return rs == null ? 0 : rs.reloadTime;
	}
	
}