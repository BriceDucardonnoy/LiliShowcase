package com.brice.lili.showcase.client.styles.toolbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;
import com.sencha.gxt.core.client.resources.StyleInjectorHelper;
import com.sencha.gxt.theme.base.client.toolbar.ToolBarBaseAppearance;

public class DarkToolBarAppearance extends ToolBarBaseAppearance {

	public interface DarkToolBarResources extends ClientBundle {
		@Source({"com/sencha/gxt/theme/base/client/toolbar/ToolBarBase.css", "DarkToolBar.css"})
		DarkToolBarStyle style();

		@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
		@Source("com/brice/lili/showcase/client/styles/black/images/black/toolbar/bg-000000.png")
		ImageResource blackBackground();
	}

	public interface DarkToolBarStyle extends ToolBarBaseStyle, CssResource {

	}

	private final DarkToolBarStyle style;
	private final DarkToolBarResources resources;

	public DarkToolBarAppearance() {this(GWT.<DarkToolBarResources> create(DarkToolBarResources.class));
	}

	public DarkToolBarAppearance(DarkToolBarResources resources) {
		this.resources = resources;
		this.style = this.resources.style();

		StyleInjectorHelper.ensureInjected(style, true);
	}

	@Override
	public String toolBarClassName() {
		return style.toolBar();
	}
}
