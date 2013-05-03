package com.brice.lili.showcase.client.core;

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
		mainImage.setUrl(url);
		// Stretch to the biggest dimension
		// FIXME BDY: old layout can be applied after render => need to rego on it to have the good layout
		mainImage.getElement().getStyle().clearHeight();
		mainImage.getElement().getStyle().clearWidth();
		// FIXME BDY: not good for square, width can be > limit width, maybe chech less than westData with and top height
		if(mainImage.getHeight() > mainImage.getWidth()) {
			mainImage.setHeight("100%");
		}
		else {
			mainImage.setWidth("100%");
		}
		imageContainer.forceLayout();
	}
}
