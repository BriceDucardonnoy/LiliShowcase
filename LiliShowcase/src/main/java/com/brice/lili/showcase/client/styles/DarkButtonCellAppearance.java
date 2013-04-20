package com.brice.lili.showcase.client.styles;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;
import com.sencha.gxt.theme.base.client.button.ButtonCellDefaultAppearance;
import com.sencha.gxt.theme.base.client.frame.Frame;
import com.sencha.gxt.theme.base.client.frame.TableFrame;

public class DarkButtonCellAppearance<C> extends ButtonCellDefaultAppearance<C> {

	public interface DarkButtonCellResources extends ButtonCellResources {

		@Override
		@ImageOptions(repeatStyle = RepeatStyle.None)
		ImageResource arrow();

		@Override
		@ImageOptions(repeatStyle = RepeatStyle.None)
		ImageResource arrowBottom();

		@Override
		@ImageOptions(repeatStyle = RepeatStyle.None)
		ImageResource split();

		@Override
		@ImageOptions(repeatStyle = RepeatStyle.None)
		ImageResource splitBottom();
		
		@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
		@Source("com/brice/lili/showcase/client/styles/black/images/black/toolbar/bg-000000.png")
		ImageResource blackBackground();
		
		@Override
		@Source({"com/sencha/gxt/theme/base/client/button/ButtonCell.css", "DarkButtonCell.css"})
		ButtonCellStyle style();
	}

	/**
	 * Creates a button cell base appearance.
	 */
	public DarkButtonCellAppearance() {
		this(GWT.<DarkButtonCellResources> create(DarkButtonCellResources.class));
	}

	/**
	 * Creates a button cell base appearance using the specified resources and
	 * templates.
	 * 
	 * @param resources the button cell resources
	 */
	public DarkButtonCellAppearance(DarkButtonCellResources resources) {
		this(resources, GWT.<ButtonCellTemplates>create(ButtonCellTemplates.class), new TableFrame(GWT.<DarkButtonTableFrameResources> create(DarkButtonTableFrameResources.class)));
	}

	/**
	 * Creates a button cell base appearance using the specified resources and
	 * templates.
	 * 
	 * @param resources the button cell resources
	 * @param templates the templates
	 * @param frame the frame
	 */
	public DarkButtonCellAppearance(DarkButtonCellResources resources, ButtonCellTemplates templates, Frame frame) {
//		this.resources = resources;
//		this.templates = templates;
//		this.frame = frame;
//
//		this.style = resources.style();
//
//		StyleInjectorHelper.ensureInjected(this.style, true);
//
//		heightOffset = frame.getFrameSize(null).getHeight();
		super(resources, templates, frame);
		Log.info("DarkButtonCellAppearance");
	}
}
