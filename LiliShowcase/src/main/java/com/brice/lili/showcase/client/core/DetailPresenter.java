package com.brice.lili.showcase.client.core;

import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.context.ApplicationContext;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent.PicturesLoadedHandler;
import com.brice.lili.showcase.client.gateKeepers.DetailGateKeeper;
import com.brice.lili.showcase.client.lang.Translate;
import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.shared.model.Picture;
import com.google.gwt.core.shared.GWT;
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

public class DetailPresenter extends Presenter<DetailPresenter.MyView, DetailPresenter.MyProxy> {

	private final Translate translate = GWT.create(Translate.class);
	
	@Inject PlaceManager placeManager;
	private String pictureFolder = "";
	private Vector<Picture> pictures = null;
	private Picture currentPicture = null;
	private String locale;
	
	public interface MyView extends View {
		public Image getMainImage();
		public void updateMainImage(String url);
		public void updateDetailInfo(String html);
	}
	
	private PicturesLoadedHandler pictureLoadedHandler = new PicturesLoadedHandler() {
		@Override
		public void onPicturesLoaded(PicturesLoadedEvent event) {
			pictures = event.getPictures();
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
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, HeaderPresenter.SLOT_mainContent, this);
	}
	
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		pictureFolder = request.getParameter(MainPagePresenter.DETAIL_KEYWORD, "wildCard");
		// PictureName is now available in onReveal and onReset method
		// If pictureName isn't ok, redirect to unauthorized page (URL set manually)
	}

	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getEventBus().addHandler(PicturesLoadedEvent.getType(), pictureLoadedHandler));
	}
	
	@Override
	protected void onReveal() {
		super.onReveal();
		Log.info("Picture folder name is " + pictureFolder);
		if(pictures == null || pictures.isEmpty()) {
			placeManager.revealErrorPlace(NameTokens.detail);
			return;
		}
		// No need to reload info, already in picture, just need to load images
		for(Picture picture : pictures) {
			if(picture.getProperty(MainPagePresenter.FILEINFO, "").equals(pictureFolder)) {
				currentPicture = picture;
				break;
			}
		}
		if(currentPicture == null) {
			placeManager.revealUnauthorizedPlace(NameTokens.detail);
		}
		// Show: picture arg/show property
		getView().updateMainImage(currentPicture.getImageUrl());
		// TODO BDY: infos
		String info = "<div style=\"" +
				"color: #DDDDDD;" +
				"font-family: helvetica; " +
				"font-size: 14px;" +
				"padding-left: 10px;" +
				"padding-left: 10px;" +
				"overflow: auto;" +
				"\">";
		info += "<div style=\"text-align: center; font-size: 18px;\"><b>" + translate.Details() + "</b></div><br />";
		info += translate.Title() + getSeparatorDependingLocale() + currentPicture.getTitle() + "<br />";
		info += translate.Dimension() + getSeparatorDependingLocale() + currentPicture.getProperty("Dimension", "") + "<br />";
		info += translate.Medium() + getSeparatorDependingLocale() + currentPicture.getProperty("Medium", "") + "<br />";
		info += translate.Date() + getSeparatorDependingLocale() + currentPicture.getProperty("Date", "") + "<br />";
		if(((String) currentPicture.getProperty("Price", "")).equalsIgnoreCase("vendu")) {
			info += translate.Price() + getSeparatorDependingLocale() + translate.Sold() + "<br />";
		}
		info += "</div>";
		getView().updateDetailInfo(info);
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
