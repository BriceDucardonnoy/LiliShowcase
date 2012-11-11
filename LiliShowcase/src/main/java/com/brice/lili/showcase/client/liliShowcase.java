package com.brice.lili.showcase.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.resources.ThemeStyles.Styles;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class liliShowcase implements EntryPoint, IsWidget {

	@UiField(provided = true)
	Styles themeStyles = ThemeStyles.getStyle();

//	interface MyUiBinder extends UiBinder<Widget, MainPageUIBinder> {
//	}
//
//	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	public void onModuleLoad() {
		Log.setCurrentLogLevel(Log.LOG_LEVEL_INFO);
		RootPanel.get().add(asWidget());
	}

	@Override
	public Widget asWidget() {
				HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
				return hlc;
//		return uiBinder.createAndBindUi(this);
	}



}
