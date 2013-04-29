
package com.brice.lili.showcase.client.gin;

import com.brice.lili.showcase.client.core.DetailPresenter;
import com.brice.lili.showcase.client.core.DetailView;
import com.brice.lili.showcase.client.core.ErrorPresenter;
import com.brice.lili.showcase.client.core.ErrorView;
import com.brice.lili.showcase.client.core.HeaderPresenter;
import com.brice.lili.showcase.client.core.HeaderView;
import com.brice.lili.showcase.client.core.MainPagePresenter;
import com.brice.lili.showcase.client.core.MainPageView;
import com.brice.lili.showcase.client.core.UnauthorizedPresenter;
import com.brice.lili.showcase.client.core.UnauthorizedView;
import com.brice.lili.showcase.client.place.ClientPlaceManager;
import com.brice.lili.showcase.client.place.DefaultPlace;
import com.brice.lili.showcase.client.place.ErrorPlace;
import com.brice.lili.showcase.client.place.NameTokens;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.brice.lili.showcase.client.core.ArtisticApproachPresenter;
import com.brice.lili.showcase.client.core.ArtisticApproachView;
import com.brice.lili.showcase.client.core.windows.ExpositionPresenter;
import com.brice.lili.showcase.client.core.windows.ExpositionView;

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

		bindPresenter(DetailPresenter.class, DetailPresenter.MyView.class,
				DetailView.class, DetailPresenter.MyProxy.class);

		bindPresenter(UnauthorizedPresenter.class,
				UnauthorizedPresenter.MyView.class, UnauthorizedView.class,
				UnauthorizedPresenter.MyProxy.class);

		bindPresenter(ArtisticApproachPresenter.class, ArtisticApproachPresenter.MyView.class,
				ArtisticApproachView.class, ArtisticApproachPresenter.MyProxy.class);

		bindPresenterWidget(ExpositionPresenter.class,
				ExpositionPresenter.MyView.class, ExpositionView.class);
	}
	
}
