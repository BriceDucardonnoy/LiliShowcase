package com.brice.lili.showcase.client.core.windows;

import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.shared.model.Picture;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.reveregroup.gwt.imagepreloader.client.FitImage;
import com.reveregroup.gwt.imagepreloader.client.FitImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.client.FitImageLoadHandler;

public class PictureViewerPresenter extends PresenterWidget<PictureViewerPresenter.MyView> {

	public interface MyView extends PopupView {
		public HTMLPanel getHtmlPanel();
		public FitImage getImage();
		public FocusPanel getFocusPanel();
		public Label getCountLabel();
		public Button getPrevButton();
		public Button getNextButton();
	}
	
	private int maxWidth;
	private int maxHeight;
	private int currentPicture;
	private int nbPictures;
	private List<Picture> pictures;
	
	@Inject
	public PictureViewerPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		currentPicture = -1;
		nbPictures = -1;
	}
	
	private KeyDownHandler keyHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if(event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
				PictureViewerPresenter.this.getView().hide();
			}
			else if(event.getNativeKeyCode() == KeyCodes.KEY_LEFT) {
				previous();
			}
			else if(event.getNativeKeyCode() == KeyCodes.KEY_RIGHT) {
				next();
			}
		}
	};
	
	private ClickHandler clickPrev = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			previous();
		}
	};
	
	private ClickHandler clickNext = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			next();
		}
	};
	
	private FitImageLoadHandler imageLoaded = new FitImageLoadHandler() {
		@Override
		public void imageLoaded(FitImageLoadEvent event) {
			getView().center();
		}
	};

	@Override
	protected void onBind() {
		super.onBind();
//		maxWidth = Utils.getScreenWidth() - 30;
//		maxHeight = Utils.getScreenHeight() - 30;
		maxWidth = Window.getClientWidth() - 30;
		maxHeight = Window.getClientHeight() /*- 20*/ - 40;
		registerHandler(getView().getFocusPanel().addKeyDownHandler(keyHandler));
		registerHandler(getView().getPrevButton().addClickHandler(clickPrev));
		registerHandler(getView().getNextButton().addClickHandler(clickNext));
		registerHandler(getView().getImage().addFitImageLoadHandler(imageLoaded));
	}
	
	@Override
	protected void onReveal() {
		Log.info("Reveal Picture Viewer");
		getView().getFocusPanel().setFocus(true);
	}
	
	public void setImage(String url) {
		getView().getImage().setUrl(url);
		getView().getImage().setMaxSize(maxWidth, maxHeight);
	}
	
	public void setPictures(List<Picture> list) {
		this.pictures = list;
	}

	public int getCurrentPicture() {
		return currentPicture;
	}

	public void setCurrentPicture(int currentPicture) {
		this.currentPicture = currentPicture;
	}
	
	public void update() {
		int sz = pictures.size();
		nbPictures = sz;
		
		Log.info("Current Picture is " + getView().getImage().getUrl());
		for(int i = 0 ; i < sz ; i++) {
			Log.info("Thumblist[" + i + "] is " + URL.encode(pictures.get(i).getImageUrl()));
			if(URL.encode(pictures.get(i).getImageUrl()).equals(getView().getImage().getUrl())) {
				setCurrentPicture(i);
				getView().getCountLabel().setText(currentPicture+1 + " / " + nbPictures);
				break;
			}
		}
		checkButtonsEnable();
	}

	public int getNbPictures() {
		return nbPictures;
	}

	public void setNbPictures(int nbPictures) {
		this.nbPictures = nbPictures;
	}
	
	private void checkButtonsEnable() {
		if(currentPicture > 0) {
			getView().getPrevButton().setEnabled(true);
		}
		else {
			getView().getPrevButton().setEnabled(false);
		}
		if(currentPicture < nbPictures - 1) {
			getView().getNextButton().setEnabled(true);
		}
		else {
			getView().getNextButton().setEnabled(false);
		}
	}
	
	private void previous() {
		if(currentPicture <= 0) return;
		currentPicture--;
		setImage(URL.encode(pictures.get(currentPicture).getImageUrl()));
		checkButtonsEnable();
		getView().getCountLabel().setText(currentPicture+1 + " / " + nbPictures);
	}
	
	private void next() {
		if(currentPicture >= nbPictures - 1) return;
		currentPicture++;
		setImage(URL.encode(pictures.get(currentPicture).getImageUrl()));
		checkButtonsEnable();
		getView().getCountLabel().setText(currentPicture+1 + " / " + nbPictures);
	}
}
