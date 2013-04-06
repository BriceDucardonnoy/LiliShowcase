package com.brice.lili.showcase.client.core;

import java.util.Vector;

import com.brice.lili.showcase.client.events.CategoryChangedEvent;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent.PicturesLoadedHandler;
import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.shared.model.Category;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public class HeaderPresenter extends Presenter<HeaderPresenter.MyView, HeaderPresenter.MyProxy> {

	@ContentSlot public static final Type<RevealContentHandler<?>> SLOT_mainContent = new Type<RevealContentHandler<?>>();
	@Inject PlaceManager placeManager;
	
	public interface MyView extends View {
		public void addGalleries(Vector<Category> categories);
		public Menu getGalleryMenu();
		public TextButton getHomeButton();
		public TextButton getGalleryButton();
		public TextButton getExpoButton();
		public TextButton getContactButton();
		public TextButton getLegalButton();
		public TextButton getLinkButton();
	}
	
	private PicturesLoadedHandler pictureLoadedHandler = new PicturesLoadedHandler() {
		@Override
		public void onPicturesLoaded(PicturesLoadedEvent event) {
			HeaderPresenter.this.getView().addGalleries(event.getCategories());
		}
	};
	
	private SelectionHandler<Item> categoryChangedHandler = new SelectionHandler<Item>() {
		@Override
		public void onSelection(SelectionEvent<Item> event) {
			MenuItem item = (MenuItem) event.getSelectedItem();
			Integer categoryId = Integer.parseInt(item.getItemId(), 10);
			if(categoryId != null) {
				getEventBus().fireEvent(new CategoryChangedEvent(categoryId));
			}
		}
	};
	
	@ProxyCodeSplit
	public interface MyProxy extends Proxy<HeaderPresenter> {
	}

	@Inject
	public HeaderPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getEventBus().addHandler(PicturesLoadedEvent.getType(), pictureLoadedHandler));
		registerHandler(getView().getGalleryMenu().addSelectionHandler(categoryChangedHandler));
		registerHandler(getView().getHomeButton().addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(placeManager.getCurrentPlaceRequest().getNameToken().equals(NameTokens.mainpage)) return;
				PlaceRequest request = new PlaceRequest(NameTokens.mainpage);
	        	placeManager.revealPlace(request);
			}
		}));
	}
	
}
