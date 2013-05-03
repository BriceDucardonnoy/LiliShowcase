package com.brice.lili.showcase.client.core;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;

public class DetailView extends ViewImpl implements DetailPresenter.MyView {

	private final Widget widget;
	@UiField BorderLayoutData westData;
	@UiField CenterLayoutContainer imageContainer;
	@UiField Image mainImage;
	
	public interface Binder extends UiBinder<Widget, DetailView> {
	}

	@Inject
	public DetailView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@Override
	public Image getMainImage() {
		return mainImage;
	}
	
	@Override
	public void updateMainImage(String url) {
		if(Log.isTraceEnabled()) {
			Log.trace("Update to image " + url);
		}
//		imageContainer.clear();
//		imageContainer.clearSizeCache();
		mainImage.setUrl(url);
		// Stretch to the biggest dimension
		// FIXME BDY: old layout can be applied after render => need to re-go on it to have the good layout
		mainImage.getElement().getStyle().clearHeight();
		mainImage.getElement().getStyle().clearWidth();
		if(mainImage.getHeight() >= mainImage.getWidth()) {
			mainImage.setHeight("100%");
			if(mainImage.getWidth() > westData.getSize()) {
				Log.info("Set width to " + Long.toString(Math.round(westData.getSize())) + "px");
				mainImage.setWidth(Long.toString(Math.round(westData.getSize())) + "px");
			}
		}
		else {
			mainImage.setWidth("100%");
			if(mainImage.getHeight() > imageContainer.getOffsetHeight(true)) {
				Log.info("Set height to " + imageContainer.getOffsetHeight(true) + "px");
				mainImage.setHeight(imageContainer.getOffsetHeight(true) + "px");
			}
		}
		imageContainer.forceLayout();
	}
}
