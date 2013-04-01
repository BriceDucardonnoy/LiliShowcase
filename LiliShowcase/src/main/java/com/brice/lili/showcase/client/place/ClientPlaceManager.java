package com.brice.lili.showcase.client.place;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class ClientPlaceManager extends PlaceManagerImpl {

	private final PlaceRequest defaultPlaceRequest;
	private final PlaceRequest errorPlaceRequest;
	
	@Inject
	public ClientPlaceManager(final EventBus eventBus,
			final TokenFormatter tokenFormatter,
			@DefaultPlace final String defaultPlaceNameToken, 
			@ErrorPlace final String errorPlaceNameToken) {
		super(eventBus, tokenFormatter);
		this.defaultPlaceRequest = new PlaceRequest(defaultPlaceNameToken);
		this.errorPlaceRequest = new PlaceRequest(errorPlaceNameToken);
	}

	@Override
	public void revealDefaultPlace() {
		revealPlace(defaultPlaceRequest, false);
	}
	
	@Override
	public void revealErrorPlace(String invalidHistoryToken) {
		revealPlace(errorPlaceRequest, false);
	}
	
	@Override
	public void revealUnauthorizedPlace(String unauthorizedHistoryToken) {
		PlaceRequest request = new PlaceRequest(NameTokens.emptyselection);
		revealPlace(request, false);
	}
	
}
