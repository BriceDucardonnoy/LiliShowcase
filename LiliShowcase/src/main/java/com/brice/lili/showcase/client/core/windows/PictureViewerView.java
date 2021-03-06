package com.brice.lili.showcase.client.core.windows;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewImpl;
import com.reveregroup.gwt.imagepreloader.client.FitImage;

public class PictureViewerView extends PopupViewImpl implements PictureViewerPresenter.MyView {

	private final Widget widget;
	@UiField FocusPanel focusPane;
	@UiField HTMLPanel pane;
	@UiField FitImage image;
	@UiField Button close;
	@UiField Button prev;
	@UiField Button next;
	@UiField Label countLabel;

	public interface Binder extends UiBinder<Widget, PictureViewerView> {
	}
	
	@Inject
	public PictureViewerView(final EventBus eventBus, final Binder binder) {
		super(eventBus);
		
		widget = binder.createAndBindUi(this);
		
		prev.setHTML("<<");// provided true if do it before createAndBindUi
		next.setHTML(">>");
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public HTMLPanel getHtmlPanel() {
		return pane;
	}

	@Override
	public FitImage getImage() {
		return image;
	}

	@Override
	public FocusPanel getFocusPanel() {
		return focusPane;
	}

	@Override
	public Label getCountLabel() {
		return countLabel;
	}
	
	@Override
	public Button getPrevButton() {
		return prev;
	}
	
	@Override
	public Button getNextButton() {
		return next;
	}

	@Override
	public Button getCloseButton() {
		return close;
	}
	
//	@UiHandler("prev")
//	void handlePrev(ClickEvent e) { }

}
