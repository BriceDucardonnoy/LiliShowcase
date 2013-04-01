package com.brice.lili.showcase.client.gateKeepers;

import com.gwtplatform.mvp.client.proxy.Gatekeeper;

public class DetailGateKeeper implements Gatekeeper {

	@Override
	public boolean canReveal() {
		// Get the parameter in URL and return true if correct
		return false;
	}

}
