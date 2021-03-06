package com.brice.lili.showcase.client.core;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.client.utils.Utils;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class ArtisticApproachPresenter extends Presenter<ArtisticApproachPresenter.MyView, ArtisticApproachPresenter.MyProxy> {

	public interface MyView extends View {
		public void setArtisticApproach(String htmlText);
		public SimpleContainer getMainPane();
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
		Utils.loadFile(loadDescriptionAC, "Documents/Presentation_" + LocaleInfo.getCurrentLocale().getLocaleName() + ".html");
	}
	
	private AsyncCallback<String> loadDescriptionAC = new AsyncCallback<String>() {
		@Override
		public void onFailure(Throwable caught) {
			Log.error("Failed to load description file: " + caught.getMessage());
			caught.printStackTrace();
		}
		@Override
		public void onSuccess(String result) {
			if(!result.contains("Error 404 NOT_FOUND")) {
				getView().setArtisticApproach(result);
			}
		}
	};
}
