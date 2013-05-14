package com.brice.lili.showcase.client.core;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.lang.Translate;
import com.brice.lili.showcase.client.properties.PictureProperties;
import com.brice.lili.showcase.client.utils.Utils;
import com.brice.lili.showcase.shared.model.Picture;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.reveregroup.gwt.imagepreloader.client.FitImage;
import com.reveregroup.gwt.imagepreloader.client.FitImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.client.FitImageLoadHandler;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class DetailView extends ViewImpl implements DetailPresenter.MyView {

	private final Translate translate = GWT.create(Translate.class);
	private final Widget widget;
	@UiField BorderLayoutContainer con;
	@UiField BorderLayoutData westData;
	@UiField BorderLayoutData thumbData;
	@UiField CenterLayoutContainer imageContainer;
	@UiField Image mainImage;
	@UiField SimpleContainer description;
//	@UiField HorizontalLayoutContainer thumbContainer;
	@UiField ListView<Picture, String> thumbList;
//	@UiField ListStore<Picture> store;
//	@UiField ValueProvider<Picture, String> pictureProvider;
	
	private ListStore<Picture> store;
	private ValueProvider<Picture, String> pictureProvider;
	
	private ArrayList<FitImage> thumbs;
	private int loadedPictures;
	private PictureProperties props = GWT.create(PictureProperties.class);
	
	public interface Binder extends UiBinder<Widget, DetailView> {
	}
	
	private interface Renderer extends XTemplates {
//		@XTemplate("<div class="{style.thumb}"><img src="{photo.pathUri}" title="{photo.name}"></div>
//<span class="x-editable">{photo.name:shorten(18)}</span>")
		@XTemplate("<div class=\"{style.thumb}\"><img src=\"{picture.imageUrl}\"></div>")
		public SafeHtml renderItem(Picture picture, Style style);
	}
	
	interface Style extends CssResource {
		String over();
		String select();
		String thumb();
		String thumbWrap();
	}
	
	interface Resources extends ClientBundle {
		@Source("DetailListView.css")
		Style css();
	}
	
	@Inject
	public DetailView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		thumbs = new ArrayList<FitImage>();
		loadedPictures = 0;
//		thumbContainer.setScrollMode(ScrollMode.AUTO);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
//	@UiFactory
//	ValueProvider<Picture, String> createPictureProvider() {
//		return props.imageUrl();
//	}
	
	@UiFactory
	ListView<Picture, String> createListView() {
//		HttpProxy<Object> proxy = new HttpProxy<Object>(new RequestBuilder(RequestBuilder.GET, ""));
//		Loader<Object, String> loader = new Loader<Object, String>(proxy);
//		loader.addLoadHandler(new ListStoreBinding<Object, Picture, List<Photo>>(store));
//	    loader.load();
		store = new ListStore<Picture>(props.key());
		pictureProvider = props.imageUrl();
		return new ListView<Picture, String>(store, pictureProvider);
	}
	
	@Override
	public Image getMainImage() {
		return mainImage;
	}
	
	@Override
	public void updateMainImage(String url) {
		if(Log.isTraceEnabled()) {
			Log.trace("Update to image " + url);
		}
		mainImage.setUrl(url);
	}
	
	@UiHandler("mainImage")
	void loadHandle(LoadEvent event) {
		// Stretch to the biggest dimension
		// TODO BDY: test with FitImage
		mainImage.getElement().getStyle().clearHeight();
		mainImage.getElement().getStyle().clearWidth();
		if(mainImage.getHeight() >= mainImage.getWidth()) {
			mainImage.setHeight("100%");
			if(mainImage.getWidth() > westData.getSize()) {
				mainImage.getElement().getStyle().clearHeight();
				Log.info("Set width to " + Long.toString(Math.round(westData.getSize())) + "px");
				mainImage.setWidth(Long.toString(Math.round(westData.getSize())) + "px");
			}
		}
		else {
			mainImage.setWidth("100%");
			if(mainImage.getHeight() > imageContainer.getOffsetHeight(true)) {
				mainImage.getElement().getStyle().clearWidth();
				Log.info("Set height to " + imageContainer.getOffsetHeight(true) + "px");
				mainImage.setHeight(imageContainer.getOffsetHeight(true) + "px");
			}
		}
		imageContainer.forceLayout();
	}

	@Override
	public void updateDetailInfo(String html) {
		description.add(new HTML(html));
	}

	@Override
	public void updateThumbs(ArrayList<String> urls) {
		loadedPictures = 0;
		thumbs.clear();
		store.clear();
//		thumbContainer.clear();
		if(!urls.isEmpty()) {
			Utils.showWaitCursor(con.getElement());
		}
		for(String url : urls) {
			FitImage im = new FitImage(url, (int) thumbData.getSize(), ((int) thumbData.getSize()) - 20, flh);// - 20: scrollbar height
			thumbs.add(im);
			Picture p = new Picture(translate.Details());
			p.getProperties().put("imageUrl", url);
			store.add(p);
//			thumbContainer.add(im);
		}
	}
	
	private FitImageLoadHandler flh = new FitImageLoadHandler() {
		@Override
		public void imageLoaded(FitImageLoadEvent event) {
			Log.info("Detail image loaded " + ((FitImage)event.getSource()).getUrl());
			loadedPictures++;
			if(loadedPictures >= thumbs.size()) {
				Utils.showDefaultCursor(con.getElement());
				thumbList.refresh();
//				thumbContainer.forceLayout();
			}
		}
	};
}
