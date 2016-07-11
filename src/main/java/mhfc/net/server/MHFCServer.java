package mhfc.net.server;

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
	@Override
	public void staticInit() {
		MHFCCommonRegistry.staticInit();
		MHFCServerRegistry.staticInit();
	}
}
