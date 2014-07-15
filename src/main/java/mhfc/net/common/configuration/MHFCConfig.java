package mhfc.net.common.configuration;

import java.io.File;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Heltrato
 *
 *         MHF Configuration Files Created for the fix of ID Conflicts in 1.6.4
 *         however this wouldnt be last as 1.7 will going be release for Forge
 *         and MCP .
 *
 *         DN: public<? for easy access; DN: int<? no dec. variables;
 *
 *
 */

public class MHFCConfig {
	public static boolean spawnTigrex;
	public static boolean spawnKirin;

	public static void init(FMLPreInitializationEvent e) {
		Configuration config = new Configuration(new File(MHFCReference.main_config_file));

		spawnTigrex = config.get(MHFCReference.main_config_mobCategory,
				MHFCReference.main_config_mobs_summonTigrex_key, true).getBoolean(true);

		config.save();
	}
}
