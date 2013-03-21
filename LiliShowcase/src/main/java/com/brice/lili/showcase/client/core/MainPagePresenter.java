package com.brice.lili.showcase.client.core;

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
		loadFile(null, GWT.getHostPageBaseURL() + "List.txt");
		getView().addCategories(categories);
//		getView().addItems(pictures);
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
		String []picts = list.split(";");
		// TODO BDY: manage categories here, not in onBind, and strip them
		for(String pict: picts) {
			if(pict.isEmpty()) continue;
			Log.info("Picture " + pict);
		}
		getView().addItems(pictures);// Initialize cover flow
		getView().init();
	}
	
	public void loadFile(final AsyncCallback<Boolean> callback, final String filename) {
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, filename);
        try {
            requestBuilder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                	Log.error("failed file reading: " + exception.getMessage());
                    if(callback != null) callback.onFailure(exception);
                }

                @Override
                public void onResponseReceived(Request request, Response response) {
                    String serialized = response.getText();
                    if(Log.isTraceEnabled()) {
                    	Log.trace("result: \n" + serialized);
                    }
                    if(callback != null) callback.onSuccess(true);
                    initPictures(serialized);
                }
            });
        } catch (RequestException e) {
            Log.error("failed file reading: " + e.getMessage());
        }
    }
}
