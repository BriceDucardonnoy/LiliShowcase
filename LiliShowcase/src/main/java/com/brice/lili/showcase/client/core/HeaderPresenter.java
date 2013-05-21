package com.brice.lili.showcase.client.core;

import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.context.ApplicationContext;
import com.brice.lili.showcase.client.core.windows.ExpositionPresenter;
import com.brice.lili.showcase.client.core.windows.LegalWindow;
import com.brice.lili.showcase.client.events.CategoryChangedEvent;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent;
import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.client.utils.Utils;
import com.brice.lili.showcase.shared.model.Category;
import com.brice.lili.showcase.shared.model.Picture;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
	private Vector<Picture> pictures;
	private Vector<Category> categories;
	private String[] picts;
	private int categoriesNumber = 0;
	
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
	
//	private PicturesLoadedHandler pictureLoadedHandler = new PicturesLoadedHandler() {
//		@Override
//		public void onPicturesLoaded(PicturesLoadedEvent event) {
//			HeaderPresenter.this.getView().addGalleries(event.getCategories());
//		}
//	};
	
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
		categories = new Vector<Category>();
		pictures = new Vector<Picture>();
		Log.info("Current local is " + LocaleInfo.getCurrentLocale().getLocaleName());
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		Utils.loadFile(loadListAC, GWT.getHostPageBaseURL() + "Documents/List.txt");
		Utils.showWaitCursor(getView().getMainCenterPane().getBody());
		
//		registerHandler(getEventBus().addHandler(PicturesLoadedEvent.getType(), pictureLoadedHandler));
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
	
	private void initPictures(String list) {
//		picts = list.replaceAll("\r", "").replaceAll("\n", "").split(";");
		picts = list.replaceAll("\r",  "").split("\n");
		Log.info("Picture " + picts[0]);
		Utils.loadFile(loadInfoAC, GWT.getHostPageBaseURL() + ApplicationContext.PHOTOSFOLDER + "/" + picts[0] + "/Details.txt");
	}
	
	private void loadPictureInfo(String infos, int nextInd) {
		// Store info
		if(!infos.isEmpty() && !infos.contains("HTTP ERROR: 404")) {
			Picture p = new Picture();
			p.addProperty(ApplicationContext.FILEINFO, picts[nextInd - 1]);
			// Special case for imageUrl to build from 'Show'
			String []entries = infos.replaceAll("\r", "").split("\n");
			for(String entry : entries) {
				if(entry.endsWith(";")) {
					entry = entry.replaceAll(";$", "");
				}
				if(entry.startsWith("Categories")) {
					// Add property Categories
					String []categories = entry.substring(entry.indexOf(":") + 1).replaceAll(" ", "").split(",");
					if(categories.length == 0) continue;
					for(String category : categories) {
						if(category.isEmpty()) continue;
						boolean found = false;
						for(Category cat : this.categories) {
							if(cat != null && cat.getName().equalsIgnoreCase(category)) {
								found = true;
								p.addCategoryId(cat.getId());
								break;
							}
						}
						if(!found) {
							if(Log.isTraceEnabled()) {
								Log.info("Add category <" + category + ">");
							}
							addNewCategory(new Category(categoriesNumber, category, category));
							p.addCategoryId(categoriesNumber++);
						}
					}
//					p.addProperty("imageUrl", GWT.getHostPageBaseURL() + "photos/" + p.getNameOrTitle() + "/" + p.getProperty("Show"));
					p.addProperty("imageUrl", GWT.getHostPageBaseURL() + ApplicationContext.PHOTOSFOLDER + "/" + picts[nextInd - 1] + "/" + p.getProperty("Show"));
					continue;
				}// End of categories process
				String []prop = entry.split(":");
				if(prop.length == 2) {
					p.addProperty(prop[0].trim(), prop[1].trim());
				}
				else {
					if(Log.isTraceEnabled()) {
						Log.warn("Line <" + entry + "> doesn't contain 2 properties");
					}
					if(prop.length == 1) p.addProperty(prop[0], null);
				}
			}// End of properties process
			pictures.add(p);
		}
		// Browse next picture
		if(nextInd < picts.length) {
			if(!picts[nextInd].isEmpty()) {
				Log.info("Picture " + picts[nextInd]);
				Utils.loadFile(loadInfoAC, GWT.getHostPageBaseURL() + ApplicationContext.PHOTOSFOLDER + "/" + picts[nextInd++] + "/Details.txt");
			}
		}
		// Launch view initialization
		else if(nextInd == picts.length) {
			Log.info("Log picture done, now init coverflow");
//			getView().addCategories(categories);
//			getView().addItems(pictures);// Initialize cover flow
//			PicturesLoadedEvent.fire(this.getEventBus(), pictures, categories);
			getView().addGalleries(categories);
			getEventBus().fireEvent(new PicturesLoadedEvent(pictures, categories));
			ApplicationContext.getInstance().addProperty("categories", categories);
			ApplicationContext.getInstance().addProperty("pictures", pictures);
			Utils.showDefaultCursor(getView().getMainCenterPane().getBody());
//			getView().init();
			return;
		}
	}
	
	private void addNewCategory(Category newCat) {
		if(newCat == null) return;
//		if(newCat.getName().equals(translate.All())) {
		if(newCat.getName().equalsIgnoreCase("tout")) {
			categories.insertElementAt(newCat, 0);
			return;
		}
		for(int i = 0 ; i < categories.size() ; i++) {
			Category cat = categories.get(i);
//			if(!cat.getName().equalsIgnoreCase(translate.All()) && newCat.getName().compareToIgnoreCase(cat.getName()) < 0) {
			if(!cat.getName().equalsIgnoreCase("tout") && newCat.getName().compareToIgnoreCase(cat.getName()) > 0) {
				categories.insertElementAt(newCat, i);
				return;
			}
		}
		categories.add(newCat);
	}
	
	/*
	 * AsyncCallbacks
	 */
	private AsyncCallback<String> loadListAC = new AsyncCallback<String>() {
		@Override
		public void onFailure(Throwable caught) {
			Log.error("Loading list failed: " + caught.getMessage());
			caught.printStackTrace();
		}
		@Override
		public void onSuccess(String result) {
			if(Log.isTraceEnabled()) {
            	Log.trace("List: \n" + result);
            }
			if(result.isEmpty()) {
				Log.error("List of pictures is empty");
			}
			else {
				initPictures(result);
			}
		}
	};
	
	private AsyncCallback<String> loadInfoAC = new AsyncCallback<String>() {
		private int ind = 0;
		@Override
		public void onFailure(Throwable caught) {
			Log.error("Loading details failed: " + caught.getMessage());
			caught.printStackTrace();
			loadPictureInfo("", ++ind);
		}
		@Override
		public void onSuccess(String result) {
			if(Log.isTraceEnabled()) {
            	Log.trace("Info: \n" + result);
            }
			if(result.isEmpty()) {
				Log.warn("List of pictures is empty");
			}
			loadPictureInfo(result, ++ind);
		}
	};
	
}
