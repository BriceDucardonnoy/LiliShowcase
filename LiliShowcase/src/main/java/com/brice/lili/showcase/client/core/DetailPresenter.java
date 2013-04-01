package com.brice.lili.showcase.client.core;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.brice.lili.showcase.client.place.NameTokens;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.brice.lili.showcase.client.core.HeaderPresenter;
import com.brice.lili.showcase.client.gateKeepers.DetailGateKeeper;

public class DetailPresenter extends Presenter<DetailPresenter.MyView, DetailPresenter.MyProxy> {

	public interface MyView extends View {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.detail)
	@UseGatekeeper(DetailGateKeeper.class)
	public interface MyProxy extends ProxyPlace<DetailPresenter> {
	}

	@Inject
	public DetailPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, HeaderPresenter.SLOT_mainContent, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
}
