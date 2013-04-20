package com.brice.lili.showcase.client.styles.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;
import com.sencha.gxt.theme.base.client.widget.HeaderDefaultAppearance;

public class DarkHeaderAppearance extends HeaderDefaultAppearance {

	public interface DarkHeaderStyle extends HeaderStyle {
		String header();

		String headerIcon();

		String headerHasIcon();

		String headerText();

		String headerBar();
	}
// TODO BDY: configure topBorder.gif and top right icon to be dark
	public interface DarkHeaderResources extends HeaderResources {

		@Source({"com/sencha/gxt/theme/base/client/widget/Header.css", "DarkHeader.css"})
		DarkHeaderStyle style();

		@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
		ImageResource headerBackground();
		
		@Source("com/brice/lili/showcase/client/styles/black/images/black/panel/white-top-bottom.gif")
		@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
		ImageResource darkHeaderBackground();
	}


	public DarkHeaderAppearance() {
		this(GWT.<DarkHeaderResources> create(DarkHeaderResources.class),
				GWT.<Template> create(Template.class));
	}

	public DarkHeaderAppearance(HeaderResources resources, Template template) {
		super(resources, template);
	}

}
