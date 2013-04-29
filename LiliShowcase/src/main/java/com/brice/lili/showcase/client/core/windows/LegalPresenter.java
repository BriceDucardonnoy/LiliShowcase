package com.brice.lili.showcase.client.core.windows;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.utils.Utils;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class LegalPresenter extends PresenterWidget<LegalPresenter.MyView> {

	public interface MyView extends PopupView {
		public SimpleContainer getMainPane();
		public void setLegal(String html);
	}

	@Inject
	public LegalPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
		Utils.loadFile(loadLegalAC, "Documents/Legal_" + LocaleInfo.getCurrentLocale().getLocaleName() + ".html");
	}
	
	private AsyncCallback<String> loadLegalAC = new AsyncCallback<String>() {
		@Override
		public void onFailure(Throwable caught) {
			Log.error("Failed to load legal file: " + caught.getMessage());
			caught.printStackTrace();
		}
		@Override
		public void onSuccess(String result) {
			if(!result.contains("Error 404 NOT_FOUND")) {
				getView().setLegal(result);
			}
		}
	};
}
