package com.brice.lili.showcase.client.core;

import com.gwtplatform.mvp.client.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class UnauthorizedView extends ViewImpl implements
		UnauthorizedPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, UnauthorizedView> {
	}

	@Inject
	public UnauthorizedView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
}
