package com.brice.lili.showcase.client.core;

import java.util.Vector;

import org.gwt.contentflow4gwt.client.ContentFlow;
import org.gwt.contentflow4gwt.client.PhotoView;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.properties.CategoryProperties;
import com.brice.lili.showcase.shared.model.Category;
import com.brice.lili.showcase.shared.model.Person;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.reveregroup.gwt.imagepreloader.client.FitImage;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.form.ComboBox;

public class MainPageView extends ViewImpl implements MainPagePresenter.MyView {

	private final Widget widget;
	@UiField ContentFlow<Person> contentFlow;
	
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

//	@UiField BorderLayoutContainer con;
	@UiField ComboBox<Category> categoriesCB;
    @UiField ContentPanel mainPane;
    @UiField(provided = true) ListStore<Category> store;
    
    private CategoryProperties props = GWT.create(CategoryProperties.class);
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
		
		store = new ListStore<Category>(props.key());
		
		widget = binder.createAndBindUi(this);
		
		testRemove = new Vector<PhotoView>();
		categoriesCB.setForceSelection(true);
		categoriesCB.setTriggerAction(TriggerAction.ALL);
		categoriesCB.setEditable(false);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void addItems(Vector<Person> people) {
		int number = people.size();
        for (final Person person : generatePeople(people, number)) {
            contentFlow.addItems(createImageView(person));
        }
    }
	
	private PhotoView createImageView(Person person) {
		testRemove.add(new PhotoView(new FitImage(person.getImageUrl()), person.getName(), person));
        return testRemove.get(testRemove.size() - 1);
    }
	
	private Person[] generatePeople(Vector<Person> people, int number) {
        Person[] result = new Person[number];

        for (int i = 0; i < number; i++) {
            result[i] = people.get(i % number);
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
	
	/*
	 * As for UiHandler, method name has no importance, it's the parameter type of the return...
	 */
	@UiFactory ContentFlow<Person> createContentFlow() {
		return new ContentFlow<Person>(true, true);
	}
	
	@UiFactory ComboBox<Category> createCategoryComboBox() {
		return new ComboBox<Category>(store, props.name());
	}

	@Override
	public void addCategories(Vector<Category> categories) {
		store.addAll(categories);
		categoriesCB.setValue(categories.get(0));
	}

	@Override
	public ComboBox<Category> getCategoriesSelecteur() {
		return categoriesCB;
	}
	
	public void categoryChanged(Integer catId) {
		Log.info("-------------------------------");
		if(catId == null) return;
		int nb = contentFlow.getNumberOfItems();
		int j;
		for(int i = nb-1 ; i >= 0 ; i--) {
			Person p = (Person) ((PhotoView)contentFlow.getItem(i)).getPojo();
			if(p == null) continue;
			int[] ids = p.getCategoryIds();
			for(j = 0 ; j < ids.length ; j++) {
				if(catId.equals(ids[j])) break;
			}
			if(j == ids.length) {
				if(!p.isVisible()) continue;
				Log.info("Remove " + p.getName());
				p.setVisible(false);
				contentFlow.removeItems(contentFlow.getItem(i));
			}
			else {
				if(p.isVisible()) continue;
				Log.info("Keep " + p.getName());
				p.setVisible(true);
				contentFlow.addItem(contentFlow.getItem(i), i);
			}
		}
	}
	
	/*
	 * This action has only consequences in the view representation => can be managed in the view
	 * As for UiFactory, method name has no importance, it's the parameter type...
	 */
	@UiHandler("categoriesCB")
	void handleSelection(SelectionEvent<Category> e) {
		/*
		 * Why "handleSelection(SelectionEvent<Category>"? because adding selection handler manually require this code:
		categoriesCB.addSelectionHandler(new SelectionHandler<Category>() {
			@Override
			public void onSelection(SelectionEvent<Category> arg0) {
				blabla
			}
		});
		* onSelection gives a SelectionEvent
		*/
//		Window.alert("Hello, AJAX");
//		Info.display("New Category", "You have selected category " + e.getSelectedItem().getId() + " " + 
//				e.getSelectedItem().getName());
		if(e.getSelectedItem() != null) {
			categoryChanged(e.getSelectedItem().getId());
		}
	}

}
