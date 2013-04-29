package com.brice.lili.showcase.client.core;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class ArtisticApproachView extends ViewImpl implements ArtisticApproachPresenter.MyView {

	private final Widget widget;
	
	@UiField HTMLPanel pane;
	@UiField SimpleContainer mainPane;

	public interface Binder extends UiBinder<Widget, ArtisticApproachView> {
	}

	@Inject
	public ArtisticApproachView(final Binder binder) {
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
	public void setArtisticApproach(String html) {
		if(Log.isTraceEnabled()) {
			Log.info("SetArtisticApproach \n" + html);
		}
		pane.getElement().setInnerHTML(html);
	}
	
}
