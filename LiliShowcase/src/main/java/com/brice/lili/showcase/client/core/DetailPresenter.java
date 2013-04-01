package com.brice.lili.showcase.client.core;

import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.place.NameTokens;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.brice.lili.showcase.client.core.HeaderPresenter;
import com.brice.lili.showcase.client.gateKeepers.DetailGateKeeper;

public class DetailPresenter extends Presenter<DetailPresenter.MyView, DetailPresenter.MyProxy> {

	private String pictureName = "";
	
	public interface MyView extends View {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.detail)
	@UseGatekeeper(DetailGateKeeper.class)
	public interface MyProxy extends ProxyPlace<DetailPresenter> {
	}

	@Inject
	public DetailPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, HeaderPresenter.SLOT_mainContent, this);
	}
	
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		pictureName = request.getParameter("name", "wildCard");
		// PictureName is now available in onReset method
		// If pictureName isn't ok, redirect to unauthorized page (URL set manually)
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		if(Log.isTraceEnabled()) {
			Log.trace("Picture selected is " + pictureName);
		}
	}
}
