package com.brice.lili.showcase.client.core;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.lang.Translate;
import com.brice.lili.showcase.client.properties.PictureProperties;
import com.brice.lili.showcase.shared.model.Picture;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.ListViewCustomAppearance;
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
	@UiField(provided=true) ListView<Picture, Picture> thumbList;
	
	private ListStore<Picture> store;
	
	private PictureProperties props = GWT.create(PictureProperties.class);
	
	public interface Binder extends UiBinder<Widget, DetailView> {
	}
	
	interface Renderer extends XTemplates {
//		@XTemplate("<div class="{style.thumb}"><img src="{photo.pathUri}" title="{photo.name}"></div>
//<span class="x-editable">{photo.name:shorten(18)}</span>")
		@XTemplate("<div class=\"{style.thumb}\"><img src=\"{picture.imageUrl}\" alt=\"Detail\"></div>")
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
		final Renderer renderer = GWT.create(Renderer.class);
		final Resources resources = GWT.create(Resources.class);
	    resources.css().ensureInjected();
	    final Style style = resources.css();
	 
	    ListViewCustomAppearance<Picture> appearance = new ListViewCustomAppearance<Picture>("." + style.thumbWrap(),
	    		style.over(), style.select()) {
	    	@Override
	    	public void renderEnd(SafeHtmlBuilder builder) {
	    		String markup = new StringBuilder("<div class=\"").append(CommonStyles.get().clear()).append("\"></div>").toString();
	    		builder.appendHtmlConstant(markup);
//	    		Utils.showDefaultCursor(con.getElement());
	    	}
	    	@Override
	    	public void renderItem(SafeHtmlBuilder builder, SafeHtml content) {
	    		builder.appendHtmlConstant("<div class='" + style.thumbWrap() + "'>");
	    		builder.append(content);
	    		builder.appendHtmlConstant("</div>");
	    	}
	    };
	    store = new ListStore<Picture>(props.key());
		thumbList = new ListView<Picture, Picture>(store, new IdentityValueProvider<Picture>() {
			@Override
			public void setValue(Picture object, Picture value) {
			}
		}, appearance);
		thumbList.setCell(new SimpleSafeHtmlCell<Picture>(new AbstractSafeHtmlRenderer<Picture>() {
			@Override
			public SafeHtml render(Picture object) {
				return renderer.renderItem(object, style);
			}
		}));
//		thumbList.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Picture>() {
//			@Override
//			public void onSelectionChanged(SelectionChangedEvent<Picture> event) {
//				TODO BDY: load image in center pane
//			}
//		});
	    
	    widget = binder.createAndBindUi(this);
	    thumbList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

	@Override
	public Widget asWidget() {
		return widget;
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
		// TESTME BDY: test with FitImage
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
		store.clear();
//		if(!urls.isEmpty()) {
//			Utils.showWaitCursor(con.getElement());
//		}
		for(String url : urls) {
			Picture p = new Picture(translate.Details());
			p.getProperties().put("imageUrl", url);
			store.add(p);
		}
	}

}
