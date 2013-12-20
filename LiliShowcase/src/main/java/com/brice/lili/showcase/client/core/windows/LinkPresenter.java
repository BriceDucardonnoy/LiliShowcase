package com.brice.lili.showcase.client.core.windows;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.utils.Utils;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.PopupView;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

public class LinkPresenter extends PresenterWidget<LinkPresenter.MyView> {

	public interface MyView extends PopupView {
		public void setLinksText(String htmlText);
	}

	@Inject
	public LinkPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
		Utils.loadFile(loadLinksAC, "Documents/Links_" + LocaleInfo.getCurrentLocale().getLocaleName() + ".html");
	}
	
	private AsyncCallback<String> loadLinksAC = new AsyncCallback<String>() {
		@Override
		public void onFailure(Throwable caught) {
			Log.error("Failed to load description file: " + caught.getMessage());
			caught.printStackTrace();
		}
		@Override
		public void onSuccess(String result) {
			if(!result.contains("Error 404 NOT_FOUND")) {
				getView().setLinksText(result);
			}
		}
	};
}
