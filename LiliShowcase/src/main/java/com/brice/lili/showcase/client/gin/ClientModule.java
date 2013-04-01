
package com.brice.lili.showcase.client.gin;

import com.brice.lili.showcase.client.place.ClientPlaceManager;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.brice.lili.showcase.client.core.HeaderPresenter;
import com.brice.lili.showcase.client.core.HeaderView;
import com.brice.lili.showcase.client.core.MainPagePresenter;
import com.brice.lili.showcase.client.core.MainPageView;
import com.brice.lili.showcase.client.place.DefaultPlace;
import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.client.core.ErrorPresenter;
import com.brice.lili.showcase.client.core.ErrorView;
import com.brice.lili.showcase.client.place.ErrorPlace;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));
		

		bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class,
				MainPageView.class, MainPagePresenter.MyProxy.class);

		bindConstant().annotatedWith(DefaultPlace.class)
				.to(NameTokens.mainpage);

		bindPresenter(ErrorPresenter.class, ErrorPresenter.MyView.class,
				ErrorView.class, ErrorPresenter.MyProxy.class);

		bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.error);

		bindPresenter(HeaderPresenter.class, HeaderPresenter.MyView.class,
				HeaderView.class, HeaderPresenter.MyProxy.class);
	}
	
}
