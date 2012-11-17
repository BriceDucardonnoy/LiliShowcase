
package com.brice.lili.showcase.client.gin;

import com.brice.lili.showcase.client.place.ClientPlaceManager;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.brice.lili.showcase.client.core.MainPagePresenter;
import com.brice.lili.showcase.client.core.MainPageView;
import com.brice.lili.showcase.client.place.DefaultPlace;
import com.brice.lili.showcase.client.place.NameTokens;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));
		

		bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class,
				MainPageView.class, MainPagePresenter.MyProxy.class);

		bindConstant().annotatedWith(DefaultPlace.class)
				.to(NameTokens.mainpage);
	}
	
}
