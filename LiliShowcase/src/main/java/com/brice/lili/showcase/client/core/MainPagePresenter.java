package com.brice.lili.showcase.client.core;

import java.util.Vector;

import org.gwt.contentflow4gwt.client.ContentFlow;
import org.gwt.contentflow4gwt.client.ContentFlowItemClickListener;

import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.shared.model.Category;
import com.brice.lili.showcase.shared.model.Picture;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
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
	}
	
	private Vector<Picture> pictures;
	private Vector<Category> categories;
	private HandlerRegistration clickHandler;
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
		initPictures();
		getView().addCategories(categories);
		getView().addItems(pictures);
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
	
	private void initPictures() {
		categories.add(new Category(0, "Category 0: All", "Bla 0"));
		categories.add(new Category(1, "Category 1", "Bla 1"));
		categories.add(new Category(2, "Category 2", "Bla 2"));
		int[] c0 = {0};
		int[] c1 = {0, 1};
		int[] c2 = {0, 2};
		// TODO BDY: get this list from request on server disk
		pictures.add(new Picture("Steve Jobs", GWT.getModuleBaseURL() + "images/photos/jobs.jpg", c0, true));
		pictures.add(new Picture("Bill Gates", GWT.getModuleName() + "/images/photos/gates.jpg", c1, true));
		pictures.add(new Picture("Sergey Brin", GWT.getModuleName() + "/images/photos/brin.jpg", c2, true));
		pictures.add(new Picture("Larry Page", GWT.getModuleName() + "/images/photos/page.jpg", c0, true));
		pictures.add(new Picture("John Doerr", GWT.getModuleName() + "/images/photos/doerr.jpg", c0, true));
		pictures.add(new Picture("Eric Schmidt", GWT.getModuleName() + "/images/photos/schmidt.jpg", c2, true));
		pictures.add(new Picture("Larry Wayne", GWT.getModuleName() + "/images/photos/wayne.jpg", c1, true));
		pictures.add(new Picture("Steve Wozniak", GWT.getModuleName() + "/images/photos/wozniak.jpg", c1, true));
		pictures.add(new Picture("John Cook", GWT.getModuleName() + "/images/photos/cook.jpg", c1, true));
	}
}
