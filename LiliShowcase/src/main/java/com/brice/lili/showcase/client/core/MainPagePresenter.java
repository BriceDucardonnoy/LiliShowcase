package com.brice.lili.showcase.client.core;

import java.util.ArrayList;
import java.util.Vector;

import org.gwt.contentflow4gwt.client.ContentFlow;
import org.gwt.contentflow4gwt.client.ContentFlowItemClickListener;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.shared.model.Category;
import com.brice.lili.showcase.shared.model.Picture;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.info.Info;

public class MainPagePresenter extends
		Presenter<MainPagePresenter.MyView, MainPagePresenter.MyProxy> {

	public interface MyView extends View {
		public ContentFlow<Picture> getContentFlow();
		public ContentPanel getMainPane();
		public void addItems(Vector<Picture> people);
		public void addCategories(Vector<Category> categories);
		public ComboBox<Category> getCategoriesSelecteur();
		public Picture getCurrentPicture();
		public void init();
	}
	
	private Vector<Picture> pictures;
	private Vector<Category> categories;
	private HandlerRegistration clickHandler;
	private String[] picts;
	
	// TODO BDY: get it from disk
	int[] c0 = {0};
	int[] c1 = {0, 1};
	int[] c2 = {0, 2};
	
	private ContentFlowItemClickListener contentFlowClickListener = new ContentFlowItemClickListener() {
        public void onItemClicked(Widget widget) {
        	Info.display("Selection", "You click on " + getView().getCurrentPicture().getName());
        	// To go on a page, set attribute target and href at same level than src (cf. contentflow_src.js line 731)
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
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		// TODO BDY: get pictures and categories list from request on server disk
		categories.add(new Category(0, "Category 0: All", "Bla 0"));
		categories.add(new Category(1, "Category 1", "Bla 1"));
		categories.add(new Category(2, "Category 2", "Bla 2"));
		loadFile(loadListAC, GWT.getHostPageBaseURL() + "List.txt");
		getView().addCategories(categories);
//		getView().addItems(pictures);
		// TODO BDY: add waiting cursor
		clickHandler = getView().getContentFlow().addItemClickListener(contentFlowClickListener);
	}
	
	@Override
	protected void onUnbind() {
		super.onUnbind();
		clickHandler.removeHandler();
	}

	@Override
	protected void onReveal() {
		super.onReveal();
//		getView().getMainPane().add(getView().getContentFlow());// Done now in UiBinder file
	}
	
	private void loadFile(final AsyncCallback<String> callback, final String filename) {
		if(filename == null || filename.isEmpty()) {
			return;
		}
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, filename);
        try {
            requestBuilder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    if(callback != null) callback.onFailure(exception);
                }
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if(callback != null) callback.onSuccess(response.getText());
                }
            });
        } catch (RequestException e) {
            Log.error("failed file reading: " + e.getMessage());
        }
    }
	
//	Log.info("getHostPageBaseURL: " + GWT.getHostPageBaseURL());// http://127.0.1.1:8888/
//	Log.info("getModuleName: " + GWT.getModuleName());// liliShowcase
//	Log.info("getModuleBaseForStaticFiles: " + GWT.getModuleBaseForStaticFiles());// http://127.0.1.1:8888/liliShowcase/ 
//	Log.info("getModuleBaseURL: " + GWT.getModuleBaseURL());// http://127.0.1.1:8888/liliShowcase/
	
	private void initPictures(String list) {
		pictures.add(new Picture("Steve Jobs", GWT.getHostPageBaseURL() + "photos/jobs.jpg", c0, true));
		pictures.add(new Picture("Bill Gates", GWT.getHostPageBaseURL() + "photos/gates.jpg", c1, true));
		pictures.add(new Picture("Sergey Brin", GWT.getHostPageBaseURL() + "photos/brin.jpg", c2, true));
		pictures.add(new Picture("Larry Page", GWT.getHostPageBaseURL() + "photos/page.jpg", c0, true));
		pictures.add(new Picture("John Doerr", GWT.getHostPageBaseURL() + "photos/doerr.jpg", c0, true));
		pictures.add(new Picture("Eric Schmidt", GWT.getHostPageBaseURL() + "photos/schmidt.jpg", c2, true));
		pictures.add(new Picture("Larry Wayne", GWT.getHostPageBaseURL() + "photos/wayne.jpg", c1, true));
		pictures.add(new Picture("Steve Wozniak", GWT.getHostPageBaseURL() + "photos/wozniak.jpg", c1, true));
		pictures.add(new Picture("John Cook", GWT.getHostPageBaseURL() + "photos/cook.jpg", c1, true));
		list = list.replaceAll("\r", "");
		list = list.replaceAll("\n", "");
		picts = list.split(";");
//		for(int i = 0 ; i < picts.length ; i++) {
//			if(picts[i].isEmpty()) continue;
			Log.info("Picture " + picts[0]);
			loadFile(loadInfoAC, GWT.getHostPageBaseURL() + "photo/" + picts[0] + "/Details.txt");
//			break;
//		}
		
//		getView().addItems(pictures);// Initialize cover flow
//		getView().init();
	}
	
	private void loadPictureInfo(String infos, int nextInd) {
		// Store info
		if(!infos.isEmpty()) {
			//pictures.add, categories etc.
		}
		// Browse next picture
		for(int i = nextInd ; i < picts.length ; i++) {
			if(picts[i].isEmpty()) continue;
			Log.info("Picture " + picts[i]);
			loadFile(loadInfoAC, GWT.getHostPageBaseURL() + "photo/" + picts[i] + "/Details.txt");
			break;
		}
		// Launch view initialization
		if(nextInd == picts.length){
			getView().addItems(pictures);// Initialize cover flow
			getView().init();
			// close waiting cursor
			return;
		}
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
