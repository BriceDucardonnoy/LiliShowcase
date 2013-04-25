package com.brice.lili.showcase.client.core;

import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.lang.Translate;
import com.brice.lili.showcase.client.styles.toolbar.DarkToolBarAppearance;
import com.brice.lili.showcase.shared.model.Category;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.menu.CheckMenuItem;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class HeaderView extends ViewImpl implements HeaderPresenter.MyView {
	
	private final Translate translate = GWT.create(Translate.class);

	private final Widget widget;
	
	@UiField ContentPanel mainCenterPane;
	@UiField TextButton homeButton;
	@UiField TextButton galleryButton;
	@UiField Menu menuGallery;
	@UiField TextButton expoButton;
	@UiField TextButton approachButton;
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
		homeButton.setTitle(translate.Home());
		galleryButton.setText(translate.Gallery());
		galleryButton.setTitle(translate.Gallery());
		expoButton.setText(translate.Expositions());
		expoButton.setTitle(translate.Expositions());
		approachButton.setText(translate.ArtisticApproach());
		approachButton.setTitle(translate.ArtisticApproachTooltip());
		contactButton.setText(translate.Contact());
		contactButton.setTitle(translate.Contact());
		legalButton.setText(translate.Legal());
		legalButton.setTitle(translate.Legal());
		linkButton.setText(translate.Link());
		linkButton.setTitle(translate.Link());
		
		mainCenterPane.setId("mainCenterPane");
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
	
	@Override
	public Menu getGalleryMenu() {
		return menuGallery;
	}
	
	@Override
	public void addGalleries(Vector<Category> categories) {
		Log.info("Categories size to add is " + categories.size());
		if(categories == null || categories.size() == 0) return ;
		menuGallery.clear();
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
	public TextButton getApproachButton() {
		return approachButton;
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
	
	@UiFactory
	ToolBar createToolbar() {
		return new ToolBar(new DarkToolBarAppearance());
	}
	
}
