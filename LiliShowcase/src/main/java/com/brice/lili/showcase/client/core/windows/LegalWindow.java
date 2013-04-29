package com.brice.lili.showcase.client.core.windows;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.lang.Translate;
import com.brice.lili.showcase.client.utils.Utils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;


public class LegalWindow extends Window {
	
	private final Translate translate = GWT.create(Translate.class);
	private SimpleContainer sc;
	
	public LegalWindow() {
		super();
		setModal(true);
		setHeadingText(translate.Legal());
		setSize("600px", "600px");
		buildUi();
	}
	
	protected void buildUi() {
		Utils.loadFile(loadLegalAC, "Documents/Legal_" + LocaleInfo.getCurrentLocale().getLocaleName() + ".html");
		sc = new SimpleContainer();
		sc.removeStyleName("font");
		sc.addStyleName("globalStyle");
		sc.addStyleName("overflowAuto");
		add(sc);
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
				sc.add(new HTML(result));
			}
		}
	};	
}
