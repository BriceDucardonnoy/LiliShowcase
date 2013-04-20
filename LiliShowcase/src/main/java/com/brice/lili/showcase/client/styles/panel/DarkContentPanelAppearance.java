package com.brice.lili.showcase.client.styles.panel;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.theme.base.client.panel.ContentPanelBaseAppearance;
import com.sencha.gxt.theme.base.client.widget.HeaderDefaultAppearance;

public class DarkContentPanelAppearance extends ContentPanelBaseAppearance {

	public interface DarkContentPanelResources extends ContentPanelResources {

		@Source({"com/sencha/gxt/theme/base/client/panel/ContentPanel.css", "DarkContentPanel.css"})
		@Override
		DarkContentPanelStyle style();

	}

	public interface DarkContentPanelStyle extends ContentPanelStyle {

	}

	public DarkContentPanelAppearance() {
		super(GWT.<DarkContentPanelResources> create(DarkContentPanelResources.class),
				GWT.<ContentPanelTemplate> create(ContentPanelTemplate.class));
	}

	public DarkContentPanelAppearance(DarkContentPanelResources resources) {
		super(resources, GWT.<ContentPanelTemplate> create(ContentPanelTemplate.class));
	}

	@Override
	public HeaderDefaultAppearance getHeaderAppearance() {
		return new DarkHeaderAppearance();
	}
}
