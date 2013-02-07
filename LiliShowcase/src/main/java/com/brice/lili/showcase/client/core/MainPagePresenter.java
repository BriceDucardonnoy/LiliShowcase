package com.brice.lili.showcase.client.core;

import java.util.Vector;

import org.gwt.contentflow4gwt.client.ContentFlow;
import org.gwt.contentflow4gwt.client.ContentFlowItemClickListener;

import com.brice.lili.showcase.client.place.NameTokens;
import com.brice.lili.showcase.shared.model.Category;
import com.brice.lili.showcase.shared.model.Person;
import com.google.gwt.core.client.GWT;
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

public class MainPagePresenter extends
		Presenter<MainPagePresenter.MyView, MainPagePresenter.MyProxy> {

	public interface MyView extends View {
		public ContentFlow<Person> getContentFlow();
		public ContentPanel getMainPane();
		public void addItems(Vector<Person> people);
		public void addCategories(Vector<Category> categories);
		public ComboBox<Category> getCategoriesSelecteur();
	}
	
	private Vector<Person> people;
	private Vector<Category> categories;

	@ProxyStandard
	@NameToken(NameTokens.mainpage)
	public interface MyProxy extends ProxyPlace<MainPagePresenter> {
	}

	@Inject
	public MainPagePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy) {
		super(eventBus, view, proxy);
		categories = new Vector<Category>();
		people = new Vector<Person>();
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		initPictures();
		getView().addCategories(categories);// Add handler on cb and consequences
		getView().addItems(people);
		getView().getContentFlow().addItemClickListener(new ContentFlowItemClickListener() {
            public void onItemClicked(Widget widget) {
//            	getView().getContentFlow().remove();
//            	getView().getContentFlow().removeItems(widget);
            	// TODO BDY: display info on picture
            }
        });
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
		int[] c0 = {0, 1};
		int[] c1 = {0};
		int[] c2 = {1};
		
		people.add(new Person("Steve Jobs", GWT.getModuleBaseURL() + "images/photos/jobs.jpg", c0));
		people.add(new Person("Bill Gates", GWT.getModuleName() + "/images/photos/gates.jpg", c1));
		people.add(new Person("Sergey Brin", GWT.getModuleName() + "/images/photos/brin.jpg", c2));
		people.add(new Person("Larry Page", GWT.getModuleName() + "/images/photos/page.jpg", c0));
		people.add(new Person("John Doerr", GWT.getModuleName() + "/images/photos/doerr.jpg", c0));
		people.add(new Person("Eric Schmidt", GWT.getModuleName() + "/images/photos/schmidt.jpg", c1));
		people.add(new Person("Larry Wayne", GWT.getModuleName() + "/images/photos/wayne.jpg", c1));
		people.add(new Person("Steve Wozniak", GWT.getModuleName() + "/images/photos/wozniak.jpg", c1));
		people.add(new Person("John Cook", GWT.getModuleName() + "/images/photos/cook.jpg", c1));
	}
}
