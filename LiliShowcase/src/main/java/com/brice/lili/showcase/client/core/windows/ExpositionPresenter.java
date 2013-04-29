package com.brice.lili.showcase.client.core.windows;

import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

public class ExpositionPresenter extends PresenterWidget<ExpositionPresenter.MyView> {

	public interface MyView extends PopupView {
		public Image getExpositionImage();
	}

	@Inject
	public ExpositionPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
}
