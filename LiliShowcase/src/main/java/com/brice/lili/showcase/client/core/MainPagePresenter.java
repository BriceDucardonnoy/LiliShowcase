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
import com.google.gwt.user.client.DOM;
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

public class MainPagePresenter extends Presenter<MainPagePresenter.MyView, MainPagePresenter.MyProxy> {

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
	private int categoriesNumber = 0;
	
	private ContentFlowItemClickListener contentFlowClickListener = new ContentFlowItemClickListener() {
        public void onItemClicked(Widget widget) {
        	Info.display("Selection", "You click on " + getView().getCurrentPicture().getTitle());
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
		loadFile(loadListAC, GWT.getHostPageBaseURL() + "List.txt");
		showWaitCursor();
		clickHandler = getView().getContentFlow().addItemClickListener(contentFlowClickListener);
	}
	
	// TODO BDY: add monitorOnWindowResize
	
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
		picts = list.replaceAll("\r", "").replaceAll("\n", "").split(";");
		Log.info("Picture " + picts[0]);
		loadFile(loadInfoAC, GWT.getHostPageBaseURL() + "photos/" + picts[0] + "/Details.txt");
	}
	
	private void loadPictureInfo(String infos, int nextInd) {
		// Store info
		if(!infos.isEmpty() && !infos.contains("HTTP ERROR: 404")) {
			System.out.println(infos);
			//pictures.add, categories etc.
			Picture p = new Picture();
			// Special case for imageUrl to build from 'Show'
			String []entries = infos.replaceAll("\r", "").replaceAll("\n", "").split(";");
			for(String entry : entries) {
				if(entry.startsWith("Categories")) {
					// Add property Categories
					String []categories = entry.substring(entry.indexOf(":") + 1).replaceAll(" ", "").split(",");
					if(categories.length == 0) continue;
//					ArrayList<Integer> catIds = new ArrayList<Integer>();
					for(String category : categories) {
						boolean found = false;
						for(Category cat : this.categories) {
							if(cat != null && cat.getName().equals(category)) {
								found = true;
//								catIds.add(cat.getId());
								p.addCategoryId(cat.getId());
								break;
							}
						}
						if(!found) {
							Log.info("Add category <" + category + ">");// Set in trace
							this.categories.add(new Category(categoriesNumber, category, category));
//							catIds.add(categoriesNumber++);
							p.addCategoryId(categoriesNumber++);
						}
					}
					p.addProperty("imageUrl", GWT.getHostPageBaseURL() + "photos/" + p.getNameOrTitle() + "/" + p.getProperty("Show"));
//					p.setCategoryIds(catIds.toArray());
					continue;
				}// End of categories process
				String []prop = entry.split(":");
				if(prop.length == 2) {
					p.addProperty(prop[0].trim(), prop[1].trim());
				}
				else {
					Log.warn("Line <" + entry + "> doesn't contain 2 properties");
					if(prop.length == 1) p.addProperty(prop[0], null);
				}
			}// End of properties process
			pictures.add(p);
		}
		// Browse next picture
		for(int i = nextInd ; i < picts.length ; i++) {
			if(picts[i].isEmpty()) continue;
			Log.info("Picture " + picts[i]);
			loadFile(loadInfoAC, GWT.getHostPageBaseURL() + "photos/" + picts[i] + "/Details.txt");
			break;
		}
		// Launch view initialization
		if(nextInd == picts.length){
			getView().addCategories(categories);
			getView().addItems(pictures);// Initialize cover flow
			getView().init();
			showDefaultCursor();
			return;
		}
	}
	
	/*
	 * Utils
	 */
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
	
	public void showWaitCursor() {
		DOM.setStyleAttribute(getView().getMainPane().getBody(), "cursor", "wait");
	}

	public void showDefaultCursor() {
		DOM.setStyleAttribute(getView().getMainPane().getBody(), "cursor", "default");
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
