package com.brice.lili.showcase.client.core.windows;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.reveregroup.gwt.imagepreloader.client.FitImage;

public class PictureViewerPresenter extends PresenterWidget<PictureViewerPresenter.MyView> {

	public interface MyView extends PopupView {
		public HTMLPanel getHtmlPanel();
		public FitImage getImage();
	}
	
	private int maxWidth;
	private int maxHeight;
	
	@Inject
	public PictureViewerPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
//		maxWidth = Utils.getScreenWidth() - 30;
//		maxHeight = Utils.getScreenHeight() - 30;
		maxWidth = Window.getClientWidth() - 30;
		maxHeight = Window.getClientHeight() /*- 20*/ - 40;
		// TODO BDY: add key controls
	}
	
	@Override
	protected void onReveal() {
		Log.info("Reveal Picture Viewer");
	}
	
	public void setImage(String url) {
		getView().getImage().setUrl(url);
		getView().getImage().setMaxSize(maxWidth, maxHeight);
	}
}
