package com.brice.lili.showcase.client.core;

import java.util.Vector;

import org.gwt.contentflow4gwt.client.ContentFlow;
import org.gwt.contentflow4gwt.client.ContentFlowItemClickListener;
import org.gwt.contentflow4gwt.client.PhotoView;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.shared.model.Person;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.reveregroup.gwt.imagepreloader.client.FitImage;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;

public class MainPageView extends ViewImpl implements MainPagePresenter.MyView {

	private final Widget widget;
	@UiField ContentFlow<Person> contentFlow;
	
	private Person[] PEOPLE;
    
	@UiField(provided = true)
	MarginData outerData = new MarginData(20);
	@UiField(provided = true)
	BorderLayoutData northData = new BorderLayoutData(100);
	@UiField(provided = true)
	BorderLayoutData westData = new BorderLayoutData(150);
	@UiField(provided = true)
	BorderLayoutData centerData = new BorderLayoutData();
//	MarginData centerData = new MarginData();
	@UiField(provided = true)
	BorderLayoutData eastData = new BorderLayoutData(150);
	@UiField(provided = true)
	BorderLayoutData southData = new BorderLayoutData(100);

	@UiField
	BorderLayoutContainer con;
    @UiField ContentPanel mainPane;
    
    private Vector<PhotoView> testRemove = null;

	public interface Binder extends UiBinder<Widget, MainPageView> {
	}

	@Inject
	public MainPageView(final Binder binder) {
		// Provided true => create them before createAndBindUi
		northData.setMargins(new Margins(5));
		westData.setMargins(new Margins(0, 5, 0, 5));
		westData.setCollapsible(true);
		westData.setSplit(true);
		eastData.setMargins(new Margins(0, 5, 0, 5));
		southData.setMargins(new Margins(5));
		centerData.setMinSize(200);
		
		widget = binder.createAndBindUi(this);
		// TODO BDY: get data from presenter
		PEOPLE = new Person[]{
				new Person("Steve Jobs", GWT.getModuleBaseURL() + "images/photos/jobs.jpg"),
				new Person("Bill Gates", GWT.getModuleName() + "/images/photos/gates.jpg"),
				new Person("Sergey Brin", GWT.getModuleName() + "/images/photos/brin.jpg"),
				new Person("Larry Page", GWT.getModuleName() + "/images/photos/page.jpg"),
				new Person("John Doerr", GWT.getModuleName() + "/images/photos/doerr.jpg"),
				new Person("Eric Schmidt", GWT.getModuleName() + "/images/photos/schmidt.jpg"),
				new Person("Larry Wayne", GWT.getModuleName() + "/images/photos/wayne.jpg"),
				new Person("Steve Wozniak", GWT.getModuleName() + "/images/photos/wozniak.jpg"),
				new Person("John Cook", GWT.getModuleName() + "/images/photos/cook.jpg")
		};
//		contentFlow = new ContentFlow<Person>(true, true);
		
//		contentFlow.setHeight("100%");
//		contentFlow.setWidth("100%");
		testRemove = new Vector<PhotoView>();
		addItems(contentFlow, PEOPLE.length);
        contentFlow.addItemClickListener(new ContentFlowItemClickListener() {
            public void onItemClicked(Widget widget) {
            	Log.warn("Clicked!");
                contentFlow.remove();
            }
        });
// TODO BDY: group by category
//        CategoryProperties props = GWT.create(CategoryProperties.class);
//        ListStore<Category> store = new ListStore<Category>(props.key());
//        store.addAll(null);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	private void addItems(ContentFlow<Person> contentFlow, int number) {
        for (final Person person : generatePeople(number)) {
            contentFlow.addItems(createImageView(person));
        }
    }
	
	private PhotoView createImageView(Person person) {
		testRemove.add(new PhotoView(new FitImage(person.getImageUrl()), person.getName()));
        return testRemove.get(testRemove.size() - 1);
    }
	
	private Person[] generatePeople(int number) {
        Person[] result = new Person[number];

        for (int i = 0; i < number; i++) {
            result[i] = PEOPLE[i % PEOPLE.length];
        }

        return result;
    }
	
	@Override
	public ContentFlow<Person> getContentFlow() {
		return contentFlow;
	}

	@Override
	public ContentPanel getMainPane() {
		return mainPane;
	}
	
	@UiFactory ContentFlow<Person> createContentFlow() {
		return new ContentFlow<Person>(true, true);
	}
}
