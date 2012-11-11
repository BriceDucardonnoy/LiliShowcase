package com.brice.lili.showcase.client;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.gin.ClientGinjector;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class liliShowcase implements EntryPoint/*, IsWidget*/ {

//	@UiField(provided = true)
//	Styles themeStyles = ThemeStyles.getStyle();
//
//	interface MyUiBinder extends UiBinder<Widget, MainPageUIBinder> {
//	}
//
//	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	private final ClientGinjector ginjector = GWT.create(ClientGinjector.class);

	public void onModuleLoad() {
		Log.setCurrentLogLevel(Log.LOG_LEVEL_INFO);
		// This is required for Gwt-Platform proxy's generator
		DelayedBindRegistry.bind(ginjector);	

		ginjector.getPlaceManager().revealCurrentPlace();
//		RootPanel.get().add(asWidget());
	}

//	@Override
//	public Widget asWidget() {
////		HorizontalLayoutContainer hlc = new HorizontalLayoutContainer();
////		return hlc;
//		return uiBinder.createAndBindUi(this);
//	}



}
