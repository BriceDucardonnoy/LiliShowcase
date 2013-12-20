package com.brice.lili.showcase.client.core.windows;

import com.allen_sauer.gwt.log.client.Log;
import com.gwtplatform.mvp.client.PopupViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

public class LinkView extends PopupViewImpl implements LinkPresenter.MyView {

	private final Widget widget;
	
	@UiField HTMLPanel pane;

	public interface Binder extends UiBinder<Widget, LinkView> {
	}

	@Inject
	public LinkView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setLinksText(String html) {
//		if(Log.isTraceEnabled()) {
			Log.info("SetLinksText \n" + html);
//		}
		pane.getElement().setInnerHTML(html);
	}
}
