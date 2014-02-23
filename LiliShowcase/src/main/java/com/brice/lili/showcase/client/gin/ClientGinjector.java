
package com.brice.lili.showcase.client.gin;

import com.brice.lili.showcase.client.core.ArtisticApproachPresenter;
import com.brice.lili.showcase.client.core.DetailPresenter;
import com.brice.lili.showcase.client.core.ErrorPresenter;
import com.brice.lili.showcase.client.core.HeaderPresenter;
import com.brice.lili.showcase.client.core.MainPagePresenter;
import com.brice.lili.showcase.client.core.UnauthorizedPresenter;
import com.brice.lili.showcase.client.gateKeepers.DetailGateKeeper;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

@GinModules({RpcDispatchAsyncModule.class, ClientModule.class})
public interface ClientGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceManager getPlaceManager();

	Provider<MainPagePresenter> getMainPagePresenter();

	Provider<ErrorPresenter> getErrorPresenter();

	AsyncProvider<HeaderPresenter> getHeaderPresenter();

	AsyncProvider<DetailPresenter> getDetailPresenter();

	DetailGateKeeper getDetailGateKeeper();

	AsyncProvider<UnauthorizedPresenter> getUnauthorizedPresenter();

	AsyncProvider<ArtisticApproachPresenter> getArtisticApproachPresenter();
}
