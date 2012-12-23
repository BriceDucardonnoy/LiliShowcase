package com.brice.lili.showcase.client.core;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.brice.lili.showcase.client.place.NameTokens;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class ErrorPresenter extends
		Presenter<ErrorPresenter.MyView, ErrorPresenter.MyProxy> {

	public interface MyView extends View {
	}

	@ProxyStandard
	@NameToken(NameTokens.error)
	public interface MyProxy extends ProxyPlace<ErrorPresenter> {
	}

	@Inject
	public ErrorPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}
}
