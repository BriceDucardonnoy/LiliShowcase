package com.brice.lili.showcase.client.core.windows;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewImpl;

public class ExpositionView extends PopupViewImpl implements ExpositionPresenter.MyView {

	private final Widget widget;
	@Inject Image expositionImage;

	public interface Binder extends UiBinder<Widget, ExpositionView> {
	}

	@Inject
	public ExpositionView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@Override
	public Image getExpositionImage() {
		return expositionImage;
	}
}
