package com.brice.lili.showcase.client.core;

import com.allen_sauer.gwt.log.client.Log;
import com.brice.lili.showcase.client.styles.panel.DarkContentPanelAppearance;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.ContentPanel;

public class ArtisticApproachView extends ViewImpl implements ArtisticApproachPresenter.MyView {

	private final Widget widget;
	
//	@UiField HtmlLayoutContainer presentationPane;
//	@UiField RichTextArea description;
	
	@UiField HTMLPanel pane;
//	@UiField ContentPanel mainPane;
//	@UiField(provided = true)
//	BorderLayoutData centerData = new BorderLayoutData();

	public interface Binder extends UiBinder<Widget, ArtisticApproachView> {
	}

	@Inject
	public ArtisticApproachView(final Binder binder) {
//		centerData.setMinSize(350);
//		centerData.setSize(1d);
		widget = binder.createAndBindUi(this);
//		presentationPane.setId("PresentationPaneID");
	}

	@Override
	public Widget asWidget() {
		return widget;
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
	
	@UiFactory
	ContentPanel createContentPanel() {
		return new ContentPanel(new DarkContentPanelAppearance());
	}
}
