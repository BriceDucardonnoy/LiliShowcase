package com.brice.lili.showcase.client.gateKeepers;

import com.gwtplatform.mvp.client.proxy.Gatekeeper;

public class DetailGateKeeper implements Gatekeeper {

	@Override
	public boolean canReveal() {
		// Add several tests if needed here and return true or false
		return false;
	}

}
