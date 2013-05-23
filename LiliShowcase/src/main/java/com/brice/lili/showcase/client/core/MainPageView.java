package com.brice.lili.showcase.client.core;

import java.util.ArrayList;
import java.util.Vector;

import org.gwt.contentflow4gwt.client.ContentFlow;
import org.gwt.contentflow4gwt.client.PhotoView;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.lang.Translate;
import com.brice.lili.showcase.client.properties.CategoryProperties;
import com.brice.lili.showcase.client.styles.panel.DarkContentPanelAppearance;
import com.brice.lili.showcase.client.utils.Utils;
import com.brice.lili.showcase.shared.model.Category;
import com.brice.lili.showcase.shared.model.Picture;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.reveregroup.gwt.imagepreloader.client.FitImage;
import com.reveregroup.gwt.imagepreloader.client.FitImageLoadEvent;
import com.reveregroup.gwt.imagepreloader.client.FitImageLoadHandler;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.info.Info;

public class MainPageView extends ViewImpl implements MainPagePresenter.MyView {

	private final Translate translate = GWT.create(Translate.class);
	
	private final Widget widget;
	@UiField ContentFlow<Picture> contentFlow;
//	@UiField BorderLayoutContainer con;
	@UiField ComboBox<Category> categoriesCB;
	@UiField ContentPanel mainPane;
	@UiField(provided = true) ListStore<Category> store;
	@UiField Radio title;
	@UiField Radio date;
	@UiField Radio dimension;
	@UiField Radio color;
	@UiField Image tr_fr;
	@UiField Image tr_en;
	@UiField FieldLabel categoryField;
	@UiField FieldLabel sortField;
	
	@UiField(provided = true)
	BorderLayoutData westData = new BorderLayoutData(150);
	@UiField(provided = true)
	BorderLayoutData centerData = new BorderLayoutData();
//	MarginData centerData = new MarginData();
	@UiField(provided = true)
	BorderLayoutData eastData = new BorderLayoutData(150);
	@UiField(provided = true)
	BorderLayoutData southData = new BorderLayoutData(170d);
	@UiField(provided = true)
	MarginData marginData = new MarginData(5);

    private CategoryProperties props = GWT.create(CategoryProperties.class);
    private ArrayList<PhotoView> allPictures = null;
    private ArrayList<Integer> orderedPictures = null;
    
    private Integer currentCategoryId = null;
    private String sortName;
    private int loadedPictures = 0;

	public interface Binder extends UiBinder<Widget, MainPageView> {
	}

	@Inject
	public MainPageView(final Binder binder) {
		// Provided true => create them before createAndBindUi
		westData.setMargins(new Margins(0, 0, 0, 0));
		eastData.setMargins(new Margins(0, 0, 0, 0));
		southData.setCollapsible(true);
		southData.setSplit(true);
		centerData.setMinSize(350);
		
		store = new ListStore<Category>(props.key());
		
		widget = binder.createAndBindUi(this);
		
		sortName = "Date";
//		sortName = title.getName();
		allPictures = new ArrayList<PhotoView>();
		orderedPictures = new ArrayList<Integer>();
		categoriesCB.setForceSelection(true);
		categoriesCB.setEditable(false);
		
		title.setBoxLabel(translate.Title());
		date.setBoxLabel(translate.Date());
		dimension.setBoxLabel(translate.Dimension());
		color.setBoxLabel(translate.Color());
		categoryField.setText(translate.Category());
		sortField.setText(translate.Sort());
		
		// No more used since menu exists
		categoryField.setVisible(false);
		
		ToggleGroup sortToggle = new ToggleGroup();
		sortToggle.add(title);
		sortToggle.add(date);
		sortToggle.add(dimension);
		sortToggle.add(color);
		// SortToggle only infer on view representation => configure it in view
		sortToggle.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {
			@Override
			public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
				ToggleGroup group = (ToggleGroup)event.getSource();
				Radio radio = (Radio)group.getValue();
				if(Log.isInfoEnabled()) {
					Info.display("Sort Changed", translate.YouveSelected() + " " + radio.getBoxLabel());
				}
				sortChanged(radio.getName());
			}
		});
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void addItems(Vector<Picture> people) {
		int number = people.size();
        for (final Picture picture : generatePictures(people, number)) {
        	createImageView(picture);
        	addInOrderedData(picture, allPictures.size() - 1);
        }
        for(Integer i : orderedPictures) {
			if(!((Picture)allPictures.get(i).getPojo()).getCategoryIds().isEmpty()) {// If belongs to no category, doens't display it
				contentFlow.addItems(allPictures.get(i));
			}
			else {
				loadedPictures++;
			}
		}
    }
	
	private FitImageLoadHandler flh = new FitImageLoadHandler() {
		@Override
		public void imageLoaded(FitImageLoadEvent event) {
			Log.info("Image loaded " + ((FitImage)event.getSource()).getUrl());
			loadedPictures++;
			if(loadedPictures >= allPictures.size()) {
				Utils.showDefaultCursor(mainPane.getBody());
			}
		}
	};
	
	private PhotoView createImageView(Picture picture) {
		String title = picture.getTitle();
		String dim = picture.getProperty("Dimension", "").toString();
		String date = picture.getProperty("Date", "").toString();
		if(title == null) title = "";
		if(dim == null) dim = "";
		if(date == null) date = "";
		allPictures.add(new PhotoView(new FitImage(picture.getImageUrl(), flh), title + "<br />" + dim + 
				(Log.isInfoEnabled() ? "<br />" + date : ""), picture));
        return allPictures.get(allPictures.size() - 1);
    }
	
	private Picture[] generatePictures(Vector<Picture> pictures, int number) {
        Picture[] result = new Picture[number];

        for (int i = 0; i < number; i++) {
            result[i] = pictures.get(i % number);
        }

        return result;
    }
	
	@Override
	public ContentFlow<Picture> getContentFlow() {
		return contentFlow;
	}

	@Override
	public ContentPanel getMainPane() {
		return mainPane;
	}
	
	@Override
	public void init() {
		Utils.showWaitCursor(mainPane.getBody());
		Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
			@Override
			public boolean execute() {
				if(getContentFlow().isAttached()) {
					getContentFlow().init();
					Log.info("Init done");
//					Utils.showDefaultCursor(mainPane.getBody());
					return false;
				}
				return true;
			}
		}, 50);
	}
	
	/*
	 * As for UiHandler, method name has no importance, it's the parameter type of the return...
	 */
	@UiFactory
	ContentFlow<Picture> createContentFlow() {
		return new ContentFlow<Picture>(true, true);
	}
	
	@UiFactory
	ComboBox<Category> createCategoryComboBox() {
		return new ComboBox<Category>(store, props.name());
	}
	
	@UiFactory
	ContentPanel createSouthCP() {
		return new ContentPanel(new DarkContentPanelAppearance());
	}

	@Override
	public void addCategories(Vector<Category> categories) {
		if(categories == null || categories.isEmpty()) return;
		store.addAll(categories);
		categoriesCB.setValue(categories.get(0));
		currentCategoryId = categories.get(0).getId();
	}

	@Override
	public ComboBox<Category> getCategoriesSelector() {
		return categoriesCB;
	}
	
	private boolean containsCategorie(ArrayList<Integer> integers, Integer cat) {
		for(int c : integers) {
			if(cat.equals(c)) return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private void addInOrderedData(Picture pojo, Integer refIdx) {
		// If POJO doesn't contain sortName property, add it at the end
		if(pojo.getProperty(sortName) == null) {
			orderedPictures.add(refIdx);
			return;
		}
		for(int i = 0 ; i < orderedPictures.size() ; i++) {
			Picture pict = (Picture) allPictures.get(orderedPictures.get(i)).getPojo();
			if(pojo == pict) continue;// Doesn't test with itself
			if(pict.getProperty(sortName) == null || 
					((Comparable<Object>)pict.getProperty(sortName)).compareTo(pojo.getProperty(sortName)) < 0) {
				orderedPictures.add(i, refIdx);
				return;
			}
		}
		orderedPictures.add(refIdx);
	}
	
	/**
	 * Display pictures for given category
	 * Clear ordered pictures and add new ones in specific order
	 * Then add it in DOM
	 */
	public void refreshCoverFlow() {
		/*
		 *  Remove objects pushed twice in target (objects from contentflow project in public)
		 */
		/*
		 *  Update data
		 */
		// Pictures are removed from view but PhotoView widget keeps last class/style state (as 'active' or 'display:block') => clean it
		for(Integer i : orderedPictures) {
			allPictures.get(i).getContainer().removeStyleName("active");
			allPictures.get(i).getContainer().setVisible(false);
		}
		orderedPictures.clear();
		int sz = allPictures.size();
		for(int i = 0 ; i < sz ; i++) {
			Picture p = (Picture) allPictures.get(i).getPojo();
			if(containsCategorie(p.getCategoryIds(), currentCategoryId)) {
				addInOrderedData(p, i);
			}
		}
		/*
		 *  Update contentFlow
		 */
		contentFlow.removeAll();
		for(Integer i : orderedPictures) {
			contentFlow.addItem(allPictures.get(i));
		}
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				if(Log.isTraceEnabled()) {
					Log.trace("Refresh DOM");
				}
				// Lame hack
				contentFlow.refreshActiveItem(orderedPictures.size() * 28);
			}
		});

	}
	
	public void sortChanged(String name) {
		sortName = name;
		refreshCoverFlow();
	}
	
	@Override
	public void resize() {
//		mainPane.forceLayout();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				if(contentFlow.isInit()) {
					contentFlow.resize();
				}
			}
		});
//		refreshCoverFlow();
	}
	
	/*
	 * ComboBox change selection event
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
		// WARNING!!! This is not bijectif with CategoryChangedEvent => menu Gallery has check icon not updated: use fireEventFromSource and compare with 'this'
		if(e.getSelectedItem() != null) {
			if(e.getSelectedItem().getId().equals(currentCategoryId)) return;
			currentCategoryId = e.getSelectedItem().getId();
			refreshCoverFlow();
		}
	}
	
	@Override
	public Picture getCurrentPicture() {
		return (Picture) ((PhotoView)contentFlow.getActiveItem()).getPojo();
	}

	@Override
	public Image getFrBtn() {
		return tr_fr;
	}

	@Override
	public Image getEnBtn() {
		return tr_en;
	}

	private void startChangeCurrentCategory(final Integer categoryId) {
		if(categoryId.equals(currentCategoryId)) return;
		currentCategoryId = categoryId;
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				refreshCoverFlow();
//				if(categoriesCB.getStore().get(categoryId) != null && categoriesCB.getStore().get(categoryId).getId().equals(categoryId)) {
//					categoriesCB.setValue(categoriesCB.getStore().get(categoryId));
//				}
			}
		});
	}
	
	@Override
	public void changeCurrentCategory(final Integer categoryId) {
		if(contentFlow.isInit() && loadedPictures >= allPictures.size()) {
			Log.info("Start change current category immediately");
			startChangeCurrentCategory(categoryId);
			return;
		}
		// Wait for cover flow to be initialized and change current category
		// That case can append if another page is loaded in first and then a category selection is done without going to home before
		Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
			@Override
			public boolean execute() {
				if(contentFlow.isInit() && loadedPictures >= allPictures.size()) {
					Log.info("Start change current category");
					startChangeCurrentCategory(categoryId);
					return false;
				}
				return true;
			}
		}, 1000);// 1s
	}

}
