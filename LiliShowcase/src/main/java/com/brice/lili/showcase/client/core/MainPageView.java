package com.brice.lili.showcase.client.core;

import org.gwt.contentflow4gwt.client.ContentFlow;
import org.gwt.contentflow4gwt.client.ContentFlowItemClickListener;
import org.gwt.contentflow4gwt.client.PhotoView;

import com.brice.lili.showcase.shared.model.Person;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.reveregroup.gwt.imagepreloader.client.FitImage;
import com.sencha.gxt.widget.core.client.ContentPanel;

public class MainPageView extends ViewImpl implements MainPagePresenter.MyView {

	private final Widget widget;
	private ContentFlow<Person> contentFlow;
	
	private Person[] PEOPLE;
	private static final int POPUP_WIDTH = 1000;
    private static final int POPUP_HEIGHT = 600;
    
//    private static final int ANIMATION_DURATION = 1000;
    @UiField ContentPanel mainPane;

	public interface Binder extends UiBinder<Widget, MainPageView> {
	}

	@Inject
	public MainPageView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		
		PEOPLE = new Person[]{
				new Person("Steve Jobs", GWT.getModuleBaseURL() + "images/photos/jobs.jpg"),
				new Person("Bill Gates", GWT.getModuleName() + "/images/photos/gates.jpg"),
				new Person("Sergey Brin", "photos/brin.jpg"),
				new Person("Larry Page", "photos/page.jpg"),
				new Person("John Doerr", "photos/doerr.jpg"),
				new Person("Eric Schmidt", "photos/schmidt.jpg"),
				new Person("Larry Wayne", "photos/wayne.jpg"),
				new Person("Steve Wozniak", "photos/wozniak.jpg"),
				new Person("John Cook", "photos/cook.jpg")
		};
//		int horizontalMargin = (Window.getClientWidth() - POPUP_WIDTH) / 2;
//		int verticalMargin = (Window.getClientHeight() - POPUP_HEIGHT) / 2;
//		final int initialLeft = Window.getClientWidth() + horizontalMargin;
		contentFlow = new ContentFlow<Person>(true, true);
		addItems(contentFlow, PEOPLE.length);
//		final ContentFlowPopupPanel<Person> popupPanel = new ContentFlowPopupPanel<Person>(contentFlow);

//        popupPanel.setSize(POPUP_WIDTH + "px", POPUP_HEIGHT + "px");
//        popupPanel.setPopupPosition(initialLeft, verticalMargin);
        contentFlow.addItemClickListener(new ContentFlowItemClickListener() {
            public void onItemClicked(Widget widget) {
                Window.alert("Clicked: ");
            }
        });
//        popupPanel.show();
//
//        animatePopupPanel(popupPanel, initialLeft, horizontalMargin, ANIMATION_DURATION);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
//	private void animatePopupPanel(final PopupPanel popupPanel, final int initialLeft, final int finalLeft, int duration) {
//        new Animation() {
//            @Override
//            protected void onUpdate(double progress) {
//                int left = (int) (((1 - progress) * initialLeft) + (progress * finalLeft));
//                popupPanel.setPopupPosition(left, 100);
//            }
//        }.run(duration);
//    }
	
	private void addItems(ContentFlow<Person> contentFlow, int number) {
        for (final Person person : generatePeople(number)) {
            contentFlow.addItems(createImageView(person));
        }
    }
	
	private PhotoView createImageView(Person person) {
        return new PhotoView(new FitImage(person.getImageUrl()), person.getName());
    }
	
	private Person[] generatePeople(int number) {
        Person[] result = new Person[number];

        for (int i = 0; i < number; i++) {
            result[i] = PEOPLE[i % PEOPLE.length];
        }

        return result;
    }
	
//	private static class ContentFlowPopupPanel<T> extends PopupPanel {
//        private final ContentFlow<T> fContentFlow;
//
//        private ContentFlowPopupPanel(ContentFlow<T> contentFlow) {
//            add(fContentFlow = contentFlow);
//        }
//
//    }

	@Override
	public ContentFlow<Person> getContentFlow() {
		return contentFlow;
	}

	@Override
	public ContentPanel getMainPane() {
		return mainPane;
	}
}
