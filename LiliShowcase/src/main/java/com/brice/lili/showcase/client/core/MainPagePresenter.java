package com.brice.lili.showcase.client.core;

import java.util.Vector;

import org.gwt.contentflow4gwt.client.ContentFlow;
import org.gwt.contentflow4gwt.client.ContentFlowItemClickListener;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.context.ApplicationContext;
import com.brice.lili.showcase.client.events.CategoryChangedEvent;
import com.brice.lili.showcase.client.events.CategoryChangedEvent.CategoryChangedHandler;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent.PicturesLoadedHandler;
import com.brice.lili.showcase.client.lang.Translate;
import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.client.utils.Utils;
import com.brice.lili.showcase.shared.model.Category;
import com.brice.lili.showcase.shared.model.Picture;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
		public void changeCurrentCategory(Integer categoryId);
		public void resize();
	}
	
	@Inject PlaceManager placeManager;
	
	private final Translate translate = GWT.create(Translate.class);
	
	private PicturesLoadedHandler pictureLoadedHandler = new PicturesLoadedHandler() {
		@Override
		public void onPicturesLoaded(PicturesLoadedEvent event) {
			initDataAndView(event.getCategories(), event.getPictures());
		}
	};
	
	private ContentFlowItemClickListener contentFlowClickListener = new ContentFlowItemClickListener() {
        public void onItemClicked(Widget widget) {
        	if(Log.isInfoEnabled()) {
        		Info.display(translate.Selection(), translate.YouClickOn() + " " + getView().getCurrentPicture().getTitle());
        	}
        	PlaceRequest request = new PlaceRequest(NameTokens.detail).with(ApplicationContext.DETAIL_KEYWORD, 
        			(String) getView().getCurrentPicture().getProperty(ApplicationContext.FILEINFO));
        	placeManager.revealPlace(request);
        }
    };
    
    private CategoryChangedHandler categoryChangedHandler = new CategoryChangedHandler() {
		@Override
		public void onCategoryChanged(CategoryChangedEvent event) {
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
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, HeaderPresenter.SLOT_mainContent, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getEventBus().addHandler(PicturesLoadedEvent.getType(), pictureLoadedHandler));
		registerHandler(getView().getContentFlow().addItemClickListener(contentFlowClickListener));
		registerHandler(getEventBus().addHandler(CategoryChangedEvent.getType(), categoryChangedHandler));
		registerHandler(getView().getFrBtn().addClickHandler(frHandler));
		registerHandler(getView().getEnBtn().addClickHandler(enHandler));
		if(ApplicationContext.getInstance().getProperty("pictures") != null) {
			initDataAndView((Vector<Category>) ApplicationContext.getInstance().getProperty("categories"), 
					(Vector<Picture>)ApplicationContext.getInstance().getProperty("pictures"));
		}
	}

	private void initDataAndView(Vector<Category> categories, Vector<Picture> pictures) {
		getView().addCategories(categories);
		getView().addItems(pictures);// Initialize cover flow
		getView().init();
	}
	
//	Log.info("getHostPageBaseURL: " + GWT.getHostPageBaseURL());// http://127.0.1.1:8888/
//	Log.info("getModuleName: " + GWT.getModuleName());// liliShowcase
//	Log.info("getModuleBaseForStaticFiles: " + GWT.getModuleBaseForStaticFiles());// http://127.0.1.1:8888/liliShowcase/ 
//	Log.info("getModuleBaseURL: " + GWT.getModuleBaseURL());// http://127.0.1.1:8888/liliShowcase/
	
}
