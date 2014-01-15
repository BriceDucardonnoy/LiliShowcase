package com.brice.lili.showcase.client.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.context.ApplicationContext;
import com.brice.lili.showcase.client.core.windows.PictureViewerPresenter;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent.PicturesLoadedHandler;
import com.brice.lili.showcase.client.gateKeepers.DetailGateKeeper;
import com.brice.lili.showcase.client.lang.Translate;
import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.shared.model.Picture;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.reveregroup.gwt.imagepreloader.client.FitImage;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;

public class DetailPresenter extends Presenter<DetailPresenter.MyView, DetailPresenter.MyProxy> {

	private final Translate translate = GWT.create(Translate.class);
	private final int MAXWAITTIME = 20;
	
	@Inject PlaceManager placeManager;
	@Inject PictureViewerPresenter pictureViewer;
	private String pictureFolder = "";
	private Vector<Picture> pictures = null;
	private Picture currentPicture = null;
	private String locale;
	private boolean arePicturesLoaded;
	private AutoProgressMessageBox waitBox;
	private int waitTime;
	
	public interface MyView extends View {
		public Image getMainImage();
		public FitImage getCenterImage();
		public void updateMainImage(String url);
		public void updateDetailInfo(String html);
		public void updateThumbs(ArrayList<String> thumbsArray);
		public List<Picture> getPicturesList();
	}
	
	private PicturesLoadedHandler pictureLoadedHandler = new PicturesLoadedHandler() {
		@Override
		public void onPicturesLoaded(PicturesLoadedEvent event) {
			pictures = event.getPictures();
			if(Log.isTraceEnabled()) {
				Log.trace("Pictures loaded: " + pictures.size());
			}
			arePicturesLoaded = true;
		}
	};
	
	private ClickHandler centerImageHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			addToPopupSlot(pictureViewer);
			pictureViewer.setPictures(getView().getPicturesList());
			pictureViewer.setImage(getView().getCenterImage().getUrl());
			pictureViewer.update();
		}
	};
	
	private RepeatingCommand loadPicturesWaitCmd = new RepeatingCommand() {
		@Override
		public boolean execute() {
			if(arePicturesLoaded) {
				waitBox.hide();
				if(initializeCurrentPicture()) {
					// Shows picture information
					showPictureMainThumbAndInfo();
					// Shows pictures thumbs
					showPicturesThumb();
				}
				return false;
			}
			if(waitTime >= MAXWAITTIME) {
				waitBox.hide();
				placeManager.revealErrorPlace(NameTokens.detail);
				return false;
			}
			waitTime++;
			return true;
		}
	};

	@ProxyCodeSplit
	@NameToken(NameTokens.detail)
	@UseGatekeeper(DetailGateKeeper.class)
	public interface MyProxy extends ProxyPlace<DetailPresenter> {
	}

	@SuppressWarnings("unchecked")
	@Inject
	public DetailPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
		super(eventBus, view, proxy);
		locale = LocaleInfo.getCurrentLocale().getLocaleName();
		pictures = (Vector<Picture>) ApplicationContext.getInstance().getProperty("pictures");
		arePicturesLoaded = false;
		waitBox = new AutoProgressMessageBox(translate.Loading());
		waitBox.setTitle(translate.Loading());
		waitBox.setMessage(translate.LoadingMessage());
		waitBox.setProgressText(translate.Loading());
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, HeaderPresenter.SLOT_mainContent, this);
	}
	
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		pictureFolder = request.getParameter(ApplicationContext.DETAIL_KEYWORD, "wildCard");
		// PictureName is now available in onReveal and onReset method
		// If pictureName isn't ok, redirect to unauthorized page (URL set manually)
	}

	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getEventBus().addHandler(PicturesLoadedEvent.getType(), pictureLoadedHandler));
		registerHandler(getView().getCenterImage().addClickHandler(centerImageHandler));
	}
	
	@Override
	protected void onReveal() {
		super.onReveal();
		// Retrieves chosen picture
		Log.info("Picture folder name is " + pictureFolder);
		if(pictures == null || pictures.isEmpty()) {
			// If pictures aren't loaded, wait for 20s that's done to display good one or redirect to error place
			if(arePicturesLoaded) {
				placeManager.revealErrorPlace(NameTokens.detail);
				return;				
			}
			waitTime = 0;
			waitBox.show();
			waitBox.auto();
			Scheduler.get().scheduleFixedDelay(loadPicturesWaitCmd, 1000);// 1s
			return;
		}
		if(initializeCurrentPicture()) {
			// Shows picture information
			showPictureMainThumbAndInfo();
			// Shows pictures thumbs
			showPicturesThumb();
		}
	}
	
	private boolean initializeCurrentPicture() {
		// No need to reload info, already in picture, just need to load images
		if(Log.isTraceEnabled()) {
			Log.trace("Picture folder: " + pictureFolder);
		}
		for(Picture picture : pictures) {
			if(Log.isTraceEnabled()) {
				Log.trace("Prop " + picture.getProperty(ApplicationContext.FILEINFO, ""));
			}
			if(picture.getProperty(ApplicationContext.FILEINFO, "").equals(pictureFolder)) {
				currentPicture = picture;
				break;
			}
		}
		if(currentPicture == null) {
			placeManager.revealUnauthorizedPlace(NameTokens.detail);
			return false;
		}
		return true;
	}
	
	private void showPictureMainThumbAndInfo() {
		// Main thumb
		getView().updateMainImage(currentPicture.getImageUrl());
		// Info
		String info = "<div style=\"" +
				"color: #DDDDDD;" +
				"font-family: helvetica; " +
				"font-size: 14px;" +
				"padding-left: 10px;" +
				"padding-left: 10px;" +
				"overflow: auto;" +
				"\">";
		info += "<div style=\"text-align: center; font-size: 18px;\"><b>" + translate.Details() + "</b></div><br />";
		info += translate.Title() + getSeparatorDependingLocale() + currentPicture.getTranslatedTitle(locale) + "<br />";
		info += translate.Dimension() + getSeparatorDependingLocale() + currentPicture.getProperty("Dimension", "") + "<br />";
		info += translate.Medium() + getSeparatorDependingLocale() + currentPicture.getProperty("Medium", "") + "<br />";
		info += translate.Date() + getSeparatorDependingLocale() + currentPicture.getProperty("Date", "") + "<br />";
		if(((String) currentPicture.getProperty("Price", "")).equalsIgnoreCase("vendu")) {
			info += translate.Price() + getSeparatorDependingLocale() + translate.Sold() + "<br />";
		}
		info += "</div>";
		getView().updateDetailInfo(info);
	}
	
	private void showPicturesThumb() {
		String details = (String) currentPicture.getProperty("Details");
		ArrayList<String> thumbsArray = new ArrayList<String>();
		if(details != null && !details.isEmpty()) {
			String thumbs[] = details.substring(details.indexOf(":") + 1).split(",");
//			thumbsArray.add(currentPicture.getImageUrl());// Add main picture to the details
			if(thumbs.length > 0) {// Add the others details
				for(String thumb : thumbs) {
					thumbsArray.add(GWT.getHostPageBaseURL() + ApplicationContext.PHOTOSFOLDER + "/" + pictureFolder + "/" + thumb.trim());
					if(Log.isTraceEnabled()) {
						Log.trace("Add detail " + thumbsArray.get(thumbsArray.size() - 1));
					}
				}
			}
		}
		getView().updateThumbs(thumbsArray);
	}
	
	private String getSeparatorDependingLocale() {
		if(locale.equals("fr")) {
			return " : ";
		}
		else if(locale.equals("en")) {
			return ": ";
		}
		else {
			return " : ";
		}
	}
	
}
