package com.brice.lili.showcase.client.core;

import java.util.Vector;

import com.brice.lili.showcase.client.core.windows.ExpositionPresenter;
import com.brice.lili.showcase.client.core.windows.LegalWindow;
import com.brice.lili.showcase.client.events.CategoryChangedEvent;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent.PicturesLoadedHandler;
import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.client.utils.Utils;
import com.brice.lili.showcase.shared.model.Category;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
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
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public class HeaderPresenter extends Presenter<HeaderPresenter.MyView, HeaderPresenter.MyProxy> {

	@ContentSlot public static final Type<RevealContentHandler<?>> SLOT_mainContent = new Type<RevealContentHandler<?>>();
	@Inject PlaceManager placeManager;
	@Inject ExpositionPresenter expositionPresenter;
	
	public interface MyView extends View {
		public void addGalleries(Vector<Category> categories);
		public Menu getGalleryMenu();
		public TextButton getHomeButton();
		public TextButton getGalleryButton();
		public TextButton getExpoButton();
		public TextButton getApproachButton();
		public TextButton getContactButton();
		public TextButton getLegalButton();
		public TextButton getLinkButton();
		public Image getFrBtn();
		public Image getEnBtn();
		public ContentPanel getMainCenterPane();
		public Image getLogo();
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
			if(!placeManager.getCurrentPlaceRequest().getNameToken().equals(NameTokens.mainpage)) {
				PlaceRequest request = new PlaceRequest(NameTokens.mainpage);
				placeManager.revealPlace(request);
			}
			MenuItem item = (MenuItem) event.getSelectedItem();
			Integer categoryId = Integer.parseInt(item.getItemId(), 10);
			if(categoryId != null) {
				getEventBus().fireEvent(new CategoryChangedEvent(categoryId));
			}
		}
	};

	private ClickHandler logoHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent arg0) {
			if(placeManager.getCurrentPlaceRequest().getNameToken().equals(NameTokens.mainpage)) return;
			PlaceRequest request = new PlaceRequest(NameTokens.mainpage);
        	placeManager.revealPlace(request);
		}
	};
	
	private SelectHandler homeHandler = new SelectHandler() {
		@Override
		public void onSelect(SelectEvent event) {
			if(placeManager.getCurrentPlaceRequest().getNameToken().equals(NameTokens.mainpage)) return;
			PlaceRequest request = new PlaceRequest(NameTokens.mainpage);
        	placeManager.revealPlace(request);
//        	placeManager.revealDefaultPlace();
		}
	};
	
	private SelectHandler approachHandler = new SelectHandler() {
		@Override
		public void onSelect(SelectEvent event) {
			if(placeManager.getCurrentPlaceRequest().getNameToken().equals(NameTokens.artisticapproach)) return;
			PlaceRequest request = new PlaceRequest(NameTokens.artisticapproach);
        	placeManager.revealPlace(request);
		}
	};
	
	private SelectHandler expositionHandler = new SelectHandler() {
		@Override
		public void onSelect(SelectEvent event) {
			addToPopupSlot(expositionPresenter);
		}
	};
	
	private SelectHandler contactHandler = new SelectHandler() {
		@Override
		public void onSelect(SelectEvent event) {
//			Window.Location.assign("mailto:contact@deblache.com?Subject=Contact%20from%20site&Body=Bonjour");
			Window.Location.assign("mailto:contact@deblache.com");
		}
	};
	
	private SelectHandler legalHandler = new SelectHandler() {
		@Override
		public void onSelect(SelectEvent event) {
			LegalWindow legal = new LegalWindow();
			legal.show();
		}
	};
	
	private ClickHandler frHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent arg0) {
			Utils.switchLocale("fr");
		}
	};

	private ClickHandler enHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent arg0) {
			Utils.switchLocale("en");
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
		registerHandler(getView().getLogo().addClickHandler(logoHandler));
		registerHandler(getView().getHomeButton().addSelectHandler(homeHandler));
		registerHandler(getView().getApproachButton().addSelectHandler(approachHandler));
		registerHandler(getView().getExpoButton().addSelectHandler(expositionHandler));
		registerHandler(getView().getLegalButton().addSelectHandler(legalHandler));
		registerHandler(getView().getContactButton().addSelectHandler(contactHandler));
		registerHandler(getView().getFrBtn().addClickHandler(frHandler));
		registerHandler(getView().getEnBtn().addClickHandler(enHandler));
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		getView().getMainCenterPane().forceLayout();
	}
}
