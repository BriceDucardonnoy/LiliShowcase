package com.brice.lili.showcase.client.core;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;

public class HeaderView extends ViewImpl implements HeaderPresenter.MyView {

	private final Widget widget;
	
	@UiField(provided = true)
	BorderLayoutData northData = new BorderLayoutData(50);
	
	@UiField ContentPanel mainCenterPane;

	public interface Binder extends UiBinder<Widget, HeaderView> {
	}

	@Inject
	public HeaderView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@Override
	public void setInSlot(Object slot, Widget content) {
		if(slot == HeaderPresenter.SLOT_mainContent) {
			mainCenterPane.clear();
			if(content != null) {
				mainCenterPane.add(content);
			}
		}
		else {
			super.setInSlot(slot, content);
		}
	}
}
