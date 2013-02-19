package com.brice.lili.showcase.client.core;

import java.util.ArrayList;
import java.util.Vector;

import org.gwt.contentflow4gwt.client.ContentFlow;
import org.gwt.contentflow4gwt.client.PhotoView;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.properties.CategoryProperties;
import com.brice.lili.showcase.shared.model.Category;
import com.brice.lili.showcase.shared.model.Person;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
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
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
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
	@UiField TextButton testBtn;
	@UiField ComboBox<Category> categoriesCB;
    @UiField ContentPanel mainPane;
    @UiField(provided = true) ListStore<Category> store;
    
    private CategoryProperties props = GWT.create(CategoryProperties.class);
    private ArrayList<PhotoView> allPictures = null;
    private ArrayList<Integer> orderedPictures = null;
    
    private Integer currentCategoryId = null;

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
		
		allPictures = new ArrayList<PhotoView>();
		orderedPictures = new ArrayList<Integer>();
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
		allPictures.add(new PhotoView(new FitImage(person.getImageUrl()), person.getName(), person));
        return allPictures.get(allPictures.size() - 1);
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
		if(categories == null || categories.isEmpty()) return;
		store.addAll(categories);
		categoriesCB.setValue(categories.get(0));
		currentCategoryId = categories.get(0).getId();
	}

	@Override
	public ComboBox<Category> getCategoriesSelecteur() {
		return categoriesCB;
	}
	
	private boolean containsCategorie(int[] cats, Integer cat) {
		for(int c : cats) {
			if(cat.equals(c)) return true;
		}
		return false;
	}
	
	private void addInOrderedData(Person pojo, Integer refIdx) {
		// TODO BDY: order data
		orderedPictures.add(refIdx);
	}
	
	/**
	 * Display pictures for given category
	 * Clear ordered pictures and add new ones in specific order
	 * Then add it in DOM
	 * @param catId the category to display
	 */
	public void categoryChanged() {
		// Update data
		orderedPictures.clear();
		int sz = allPictures.size();
		for(int i = 0 ; i < sz ; i++) {
			Person p = (Person) allPictures.get(i).getPojo();
			if(containsCategorie(p.getCategoryIds(), currentCategoryId)) {
				addInOrderedData(p, i);
			}
		}
		// Update contentFlow
//		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
//			@Override
//			public void execute() {
				contentFlow.removeAll();
//				Log.info("Back from removeAll");
				for(Integer i : orderedPictures) {
					contentFlow.addItem(allPictures.get(i));
				}
				
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						Log.info("Refresh DOM");
						// TODO BDY: look for a listener in JS
						contentFlow.refreshActiveItem();
						contentFlow.step();
						contentFlow.moveTo(0);
					}
				});
//			}
//		});
		
	}
	
	public void sortChanged() {
		// TODO BDY: add radio button to select order: Period, date, price, size (always sub-ordered with name)		
	}
	
	/*
	 * This action has only consequences in the view representation => can be managed in the view
	 * As for UiFactory, method name has no importance, it's the parameter type...
	 */
	@UiHandler("categoriesCB")
	void handleSelection(SelectionEvent<Category> e) {
		/*
		 * Why "handleSelection(SelectionEvent<Category>"? because ComboBox has method addSelectionHandler.
		 * Adding selection handler manually require this code:
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
			if(e.getSelectedItem().getId().equals(currentCategoryId)) return;
			currentCategoryId = e.getSelectedItem().getId();
			categoryChanged();
		}
	}
	
	@UiHandler("testBtn")
	void handleClick(SelectEvent e) {
		contentFlow.refreshActiveItem();
		contentFlow.moveTo(0);
	}

}
