package mhfc.net.server;

import mhfc.net.MHFCMain;
import mhfc.net.ProxyBase;
import mhfc.net.common.core.MHFCCommonRegistry;
import mhfc.net.server.core.MHFCServerRegistry;

/**
 * A proxy for the server, registering server only stuff.
 *
 * @author WorldSEnder
 *
 */
public class MHFCServer extends ProxyBase {
	public void initialize() {
		MHFCCommonRegistry.init();
		MHFCServerRegistry.init();
	}

	@Override
	public void staticInit() {
		MHFCCommonRegistry.staticInit();
		MHFCServerRegistry.staticInit();

		MHFCMain.initPhase.registerEntryCallback(e -> initialize());
	}
}
