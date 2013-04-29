package com.brice.lili.showcase.client.core.windows;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewImpl;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class LegalView extends PopupViewImpl implements LegalPresenter.MyView {

	private final Widget widget;
	@UiField HTMLPanel pane;
	@UiField SimpleContainer mainPane;

	public interface Binder extends UiBinder<Widget, LegalView> {
	}

	@Inject
	public LegalView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@Override
	public SimpleContainer getMainPane() {
		return mainPane;
	}
	
	@Override
	public void setLegal(String html) {
		pane.getElement().setInnerHTML(html);
	}
}
