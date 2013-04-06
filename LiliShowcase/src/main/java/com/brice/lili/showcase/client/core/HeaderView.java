package com.brice.lili.showcase.client.core;

import com.brice.lili.showcase.client.lang.Translate;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public class HeaderView extends ViewImpl implements HeaderPresenter.MyView {
	
	private final Translate translate = GWT.create(Translate.class);

	private final Widget widget;
	
	@UiField(provided = true)
	BorderLayoutData northData = new BorderLayoutData(50);
	
	@UiField ContentPanel mainCenterPane;
	@UiField TextButton homeButton;
	@UiField TextButton galleryButton;
	@UiField Menu menuGallery;
	@UiField TextButton expoButton;
	@UiField TextButton contactButton;
	@UiField TextButton legalButton;
	@UiField TextButton linkButton;

	public interface Binder extends UiBinder<Widget, HeaderView> {
	}

	@Inject
	public HeaderView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		
		homeButton.setText(translate.Home());
		galleryButton.setText(translate.Gallery());
		expoButton.setText(translate.Expositions());
		contactButton.setText(translate.Contact());
		legalButton.setText(translate.Legal());
		linkButton.setText(translate.Link());
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
	
	@UiHandler(value = {"homeButton", "expoButton", "contactButton", "legalButton", "linkButton"})
	void handleClick(SelectEvent e) {
		Info.display("INFO", ((TextButton) e.getSource()).getText());
	}
	
	@UiHandler("menuGallery")
	public void onMenuSelection(SelectionEvent<Item> event) {
		MenuItem item = (MenuItem) event.getSelectedItem();
		Info.display("Action", translate.YouClickOn() + " " + item.getText());
	}
	
}
