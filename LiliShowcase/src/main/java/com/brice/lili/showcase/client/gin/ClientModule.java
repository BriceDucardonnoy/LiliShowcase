
package com.brice.lili.showcase.client.gin;

import com.brice.lili.showcase.client.place.ClientPlaceManager;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));
		
	}
	
}
