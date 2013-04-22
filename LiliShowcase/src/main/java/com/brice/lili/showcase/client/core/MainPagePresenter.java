package com.brice.lili.showcase.client.core;

import java.util.Vector;

import org.gwt.contentflow4gwt.client.ContentFlow;
import org.gwt.contentflow4gwt.client.ContentFlowItemClickListener;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.events.CategoryChangedEvent;
import com.brice.lili.showcase.client.events.CategoryChangedEvent.CategoryChangedHandler;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent;
import com.brice.lili.showcase.client.lang.Translate;
import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.client.utils.Utils;
import com.brice.lili.showcase.shared.model.Category;
import com.brice.lili.showcase.shared.model.Picture;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.info.Info;

public class MainPagePresenter extends Presenter<MainPagePresenter.MyView, MainPagePresenter.MyProxy> {

	public interface MyView extends View {
		public ContentFlow<Picture> getContentFlow();
		public ContentPanel getMainPane();
		public void addItems(Vector<Picture> people);
		public void addCategories(Vector<Category> categories);
		public ComboBox<Category> getCategoriesSelector();
		public Picture getCurrentPicture();
		public void init();
		public Image getFrBtn();
		public Image getEnBtn();
		public void setDescriptionText(String text);
		public void changeCurrentCategory(Integer categoryId);
	}
	
	public static final String DETAIL_KEYWORD = "picture";
	
	@Inject PlaceManager placeManager;
	
	private final Translate translate = GWT.create(Translate.class);
	
	private Vector<Picture> pictures;
	private Vector<Category> categories;
	private String[] picts;
	private int categoriesNumber = 0;
	
	private ContentFlowItemClickListener contentFlowClickListener = new ContentFlowItemClickListener() {
        public void onItemClicked(Widget widget) {
        	Info.display(translate.Selection(), translate.YouClickOn() + " " + getView().getCurrentPicture().getTitle());
        	PlaceRequest request = new PlaceRequest(NameTokens.detail).with(DETAIL_KEYWORD, getView().getCurrentPicture().getTitleOrName());
        	placeManager.revealPlace(request);
        }
    };
    
    private CategoryChangedHandler categoryChangedHandler = new CategoryChangedHandler() {
		@Override
		public void onCategoryChanged(CategoryChangedEvent event) {
			if(!placeManager.getCurrentPlaceRequest().getNameToken().equals(NameTokens.mainpage)) {
				PlaceRequest request = new PlaceRequest(NameTokens.mainpage);
				placeManager.revealPlace(request);
			}
			MainPagePresenter.this.getView().changeCurrentCategory(event.getCategoryId());
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
	
	@ProxyStandard
	@NameToken(NameTokens.mainpage)
	public interface MyProxy extends ProxyPlace<MainPagePresenter> {
	}

	@Inject
	public MainPagePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
		super(eventBus, view, proxy);
		categories = new Vector<Category>();
		pictures = new Vector<Picture>();
		Log.info("Current local is " + LocaleInfo.getCurrentLocale().getLocaleName());
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, HeaderPresenter.SLOT_mainContent, this);
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		Utils.loadFile(loadListAC, GWT.getHostPageBaseURL() + "List.txt");
		Utils.loadFile(loadDescriptionAC, "Presentation_" + LocaleInfo.getCurrentLocale().getLocaleName() + ".html");
		Utils.showWaitCursor(getView().getMainPane().getBody());
		registerHandler(getView().getContentFlow().addItemClickListener(contentFlowClickListener));
		registerHandler(getEventBus().addHandler(CategoryChangedEvent.getType(), categoryChangedHandler));
		registerHandler(getView().getFrBtn().addClickHandler(frHandler));
		registerHandler(getView().getEnBtn().addClickHandler(enHandler));
	}

//	@Override
//	protected void onReveal() {
//		super.onReveal();
//		getView().getMainPane().add(getView().getContentFlow());// Done now in UiBinder file
//	}
	
//	Log.info("getHostPageBaseURL: " + GWT.getHostPageBaseURL());// http://127.0.1.1:8888/
//	Log.info("getModuleName: " + GWT.getModuleName());// liliShowcase
//	Log.info("getModuleBaseForStaticFiles: " + GWT.getModuleBaseForStaticFiles());// http://127.0.1.1:8888/liliShowcase/ 
//	Log.info("getModuleBaseURL: " + GWT.getModuleBaseURL());// http://127.0.1.1:8888/liliShowcase/
	
	private void initPictures(String list) {
//		picts = list.replaceAll("\r", "").replaceAll("\n", "").split(";");
		picts = list.replaceAll("\r",  "").split("\n");
		Log.info("Picture " + picts[0]);
		Utils.loadFile(loadInfoAC, GWT.getHostPageBaseURL() + "photos/" + picts[0] + "/Details.txt");
	}
	
	private void loadPictureInfo(String infos, int nextInd) {
		// Store info
		if(!infos.isEmpty() && !infos.contains("HTTP ERROR: 404")) {
			Picture p = new Picture();
			// Special case for imageUrl to build from 'Show'
//			String []entries = infos.replaceAll("\r", "").replaceAll("\n", "").split(";");
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
					p.addProperty("imageUrl", GWT.getHostPageBaseURL() + "photos/" + picts[nextInd - 1] + "/" + p.getProperty("Show"));
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
				Utils.loadFile(loadInfoAC, GWT.getHostPageBaseURL() + "photos/" + picts[nextInd++] + "/Details.txt");
			}
		}
		// Launch view initialization
		else if(nextInd == picts.length) {
			Log.info("Log picture done, now init coverflow");
			getView().addCategories(categories);
			getView().addItems(pictures);// Initialize cover flow
//			PicturesLoadedEvent.fire(this.getEventBus(), pictures, categories);
			getEventBus().fireEvent(new PicturesLoadedEvent(pictures, categories));
			Utils.showDefaultCursor(getView().getMainPane().getBody());
			getView().init();
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
	
	private AsyncCallback<String> loadDescriptionAC = new AsyncCallback<String>() {
		@Override
		public void onFailure(Throwable caught) {
			Log.error("Failed to load description file: " + caught.getMessage());
			caught.printStackTrace();
		}
		@Override
		public void onSuccess(String result) {
			if(!result.contains("Error 404 NOT_FOUND")) {
				getView().setDescriptionText(result);
			}
		}
	};
	
}
