package com.brice.lili.showcase.client.core;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class ArtisticApproachView extends ViewImpl implements ArtisticApproachPresenter.MyView {

	private final Widget widget;
	
//	@UiField HtmlLayoutContainer presentationPane;
	
	@UiField HTMLPanel pane;
	@UiField SimpleContainer mainPane;
//	@UiField ContentPanel mainPane;

	public interface Binder extends UiBinder<Widget, ArtisticApproachView> {
	}

	@Inject
	public ArtisticApproachView(final Binder binder) {
		widget = binder.createAndBindUi(this);
//		presentationPane.setId("PresentationPaneID");
	}

	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@Override
	public SimpleContainer getMainPane() {
		return mainPane;
	}
	
//	public interface HtmlLayoutContainerTemplate extends XTemplates {
//		@XTemplate("<div width=\"100%\" height=\"100%\" class=\"approach\" />")
//		SafeHtml getTemplate();
//	}
//	
//	@UiFactory
//	HtmlLayoutContainer createHtmlLayoutContainer() {
//		HtmlLayoutContainerTemplate templates = GWT.create(HtmlLayoutContainerTemplate.class);
//	    return new HtmlLayoutContainer(templates.getTemplate());
//	}
	
	@Override
	public void setArtisticApproach(String html) {
		Log.info("SetArtisticApproach \n" + html);
//		HTMLPanel p = new HTMLPanel(html);
//		presentationPane.add(p, new HtmlData(".approach"));
//		description.setHTML(html);
		pane.getElement().setInnerHTML(html);
		
//		mainPane.clear();
//		mainPane.setWidget(new HTML(html));
//		mainPane.setWidget(new HTML("<h1 style=\"color: red\">PATATE</h1>"));// Works with only that in presentation_default.html
	}
	
}
