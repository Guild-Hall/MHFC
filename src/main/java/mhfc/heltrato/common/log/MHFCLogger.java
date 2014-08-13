package mhfc.heltrato.common.log;

import mhfc.heltrato.common.util.lib.MHFCReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MHFCLogger {
	
	public static final Logger logger = LogManager.getLogger("MHFC");
	
	public static void readyLog() {
		 logger.info("Starting MHFC" + MHFCReference.main_version);
		 logger.info("Copyright (c) Heltrato & The Modding Team 2014");
	}
}
