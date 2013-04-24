package com.brice.lili.showcase.client.core;

import com.brice.lili.showcase.client.place.NameTokens;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class ArtisticApproachPresenter extends Presenter<ArtisticApproachPresenter.MyView, ArtisticApproachPresenter.MyProxy> {

	public interface MyView extends View {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.artisticapproach)
	public interface MyProxy extends ProxyPlace<ArtisticApproachPresenter> {
	}

	@Inject
	public ArtisticApproachPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
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
