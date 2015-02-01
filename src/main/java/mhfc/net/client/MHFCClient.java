package mhfc.net.client;

import mhfc.net.ProxyBase;
import mhfc.net.client.core.MHFCClientRegistry;
import mhfc.net.common.core.MHFCCommonRegistry;

public class MHFCClient extends ProxyBase {
	@Override
	public void register() {
		MHFCCommonRegistry.init();
		MHFCClientRegistry.init();
	}
}
