package com.brice.lili.showcase.client.core;

import java.util.Vector;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent;
import com.brice.lili.showcase.client.events.PicturesLoadedEvent.PicturesLoadedHandler;
import com.brice.lili.showcase.client.gateKeepers.DetailGateKeeper;
import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.shared.model.Picture;
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

	@Inject PlaceManager placeManager;
	private String pictureFolder = "";
	private Vector<Picture> pictures = null;
	private Picture currentPicture = null;
	
	public interface MyView extends View {
		public Image getMainImage();
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

	@Inject
	public DetailPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
		super(eventBus, view, proxy);
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
		getView().getMainImage().setUrl(currentPicture.getImageUrl());
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		if(Log.isTraceEnabled()) {
			Log.trace("Picture selected is " + pictureFolder);
		}
	}
}
