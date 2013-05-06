package com.brice.lili.showcase.client.gateKeepers;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.context.ApplicationContext;
import com.brice.lili.showcase.client.place.NameTokens;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

public class DetailGateKeeper implements Gatekeeper {

	@Inject PlaceManager placeManager;
	
	@Override
	public boolean canReveal() {
		if(Log.isTraceEnabled()) {
			Log.trace(placeManager.getCurrentPlaceRequest().getNameToken() + ": " + 
					placeManager.getCurrentPlaceRequest().getParameter(ApplicationContext.DETAIL_KEYWORD, ""));
		}
		
		if(placeManager.getCurrentPlaceRequest().getNameToken().equals(NameTokens.detail) &&
				!placeManager.getCurrentPlaceRequest().getParameter(ApplicationContext.DETAIL_KEYWORD, "").isEmpty()) {
			return true;
		}
		return false;
	}

}
