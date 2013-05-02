package com.brice.lili.showcase.client.core;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class DetailView extends ViewImpl implements DetailPresenter.MyView {

	private final Widget widget;
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
}
