package com.brice.lili.showcase.client.core;

import java.util.Vector;

import com.brice.lili.showcase.client.lang.Translate;
import com.brice.lili.showcase.shared.model.Category;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.menu.CheckMenuItem;
import com.sencha.gxt.widget.core.client.menu.Menu;

public class HeaderView extends ViewImpl implements HeaderPresenter.MyView {
	
	private final Translate translate = GWT.create(Translate.class);

	private final Widget widget;
	
	@UiField ContentPanel mainCenterPane;
	@UiField TextButton homeButton;
	@UiField TextButton galleryButton;
	@UiField Menu menuGallery;
	@UiField TextButton expoButton;
	@UiField TextButton contactButton;
	@UiField TextButton legalButton;
	@UiField TextButton linkButton;
	@UiField Image tr_fr;
	@UiField Image tr_en;

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
	
//	@UiHandler(value = {"homeButton", "expoButton", "contactButton", "legalButton", "linkButton"})
//	void handleClick(SelectEvent e) {
//		Info.display("INFO", ((TextButton) e.getSource()).getText());
//	}
//	
//	@UiHandler("menuGallery")
//	public void onMenuSelection(SelectionEvent<Item> event) {
//		MenuItem item = (MenuItem) event.getSelectedItem();
//		Info.display("Action", translate.YouClickOn() + " " + item.getText());
//	}
	
	@Override
	public Menu getGalleryMenu() {
		return menuGallery;
	}

	@Override
	public void addGalleries(Vector<Category> categories) {
		if(categories == null || categories.size() == 0) return;
		for(Category category : categories) {
			CheckMenuItem item = new CheckMenuItem(category.getName());
			item.setGroup("gallery");
			item.setItemId(category.getId().toString());
			menuGallery.add(item);
		}
		((CheckMenuItem)menuGallery.getItemByItemId(categories.get(0).getId().toString())).setChecked(true);
//		menuGallery.setActiveItem(menuGallery.getItemByItemId(categories.get(1).getId().toString()), false);
	}

	@Override
	public TextButton getHomeButton() {
		return homeButton;
	}

	@Override
	public TextButton getGalleryButton() {
		return galleryButton;
	}

	@Override
	public TextButton getExpoButton() {
		return expoButton;
	}

	@Override
	public TextButton getContactButton() {
		return contactButton;
	}

	@Override
	public TextButton getLegalButton() {
		return legalButton;
	}

	@Override
	public TextButton getLinkButton() {
		return linkButton;
	}
	
	@Override
	public Image getFrBtn() {
		return tr_fr;
	}

	@Override
	public Image getEnBtn() {
		return tr_en;
	}
	
}
