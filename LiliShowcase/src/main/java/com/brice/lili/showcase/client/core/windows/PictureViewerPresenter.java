package com.brice.lili.showcase.client.core.windows;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.reveregroup.gwt.imagepreloader.client.FitImage;

public class PictureViewerPresenter extends PresenterWidget<PictureViewerPresenter.MyView> {

	public interface MyView extends PopupView {
		public HTMLPanel getHtmlPanel();
		public FitImage getImage();
		public FocusPanel getFocusPanel();
		public Label getCountLabel();
	}
	
	private int maxWidth;
	private int maxHeight;
	private int currentPicture;
	private int nbPictures;
	
	@Inject
	public PictureViewerPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
		currentPicture = -1;
		nbPictures = -1;
	}
	
	private KeyDownHandler keyHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent event) {
			Log.info("Key press event: " + event.getNativeEvent().getKeyCode());
			if(event.isMetaKeyDown()) {
				Log.info("Escape pressed");
			}
		}
	};

	@Override
	protected void onBind() {
		super.onBind();
//		maxWidth = Utils.getScreenWidth() - 30;
//		maxHeight = Utils.getScreenHeight() - 30;
		maxWidth = Window.getClientWidth() - 30;
		maxHeight = Window.getClientHeight() /*- 20*/ - 40;
		// TODO BDY: try with key down
		registerHandler(getView().getFocusPanel().addKeyDownHandler(keyHandler));
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

	public int getCurrentPicture() {
		return currentPicture;
	}

	public void setCurrentPicture(int currentPicture) {
		this.currentPicture = currentPicture;
		getView().getCountLabel().setText(currentPicture + " / " + nbPictures);
	}

	public int getNbPictures() {
		return nbPictures;
	}

	public void setNbPictures(int nbPictures) {
		this.nbPictures = nbPictures;
		getView().getCountLabel().setText(currentPicture + " / " + nbPictures);
	}
}
