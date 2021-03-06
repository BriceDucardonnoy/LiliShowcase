package com.brice.lili.showcase.client.styles.button.frame;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;
import com.sencha.gxt.theme.base.client.button.ButtonTableFrameResources;
import com.sencha.gxt.theme.base.client.frame.TableFrame.TableFrameStyle;

public interface DarkButtonTableFrameResources extends ButtonTableFrameResources {

	@Source({"com/sencha/gxt/theme/base/client/frame/TableFrame.css", "com/sencha/gxt/theme/base/client/button/ButtonTableFrame.css", "DarkButtonTableFrame.css"})
	@Override
	TableFrameStyle style();

	@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
	@Source("com/brice/lili/showcase/client/styles/black/images/black/toolbar/bg-000000.png")
	ImageResource background();

	@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
	ImageResource backgroundOverBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
	ImageResource backgroundPressedBorder();

	@Override
	ImageResource topLeftBorder();

	ImageResource topLeftOverBorder();

	ImageResource topLeftPressedBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
	@Override
	ImageResource topBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
	ImageResource topOverBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
	ImageResource topPressedBorder();

	@Override
	ImageResource topRightBorder();

	ImageResource topRightOverBorder();

	ImageResource topRightPressedBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Vertical)
	@Override
	ImageResource leftBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Vertical)
	ImageResource leftOverBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Vertical)
	ImageResource leftPressedBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Vertical)
	@Override
	ImageResource rightBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Vertical)
	ImageResource rightOverBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Vertical)
	ImageResource rightPressedBorder();

	@Override
	ImageResource bottomLeftBorder();

	ImageResource bottomLeftOverBorder();

	ImageResource bottomLeftPressedBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
	@Override
	ImageResource bottomBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
	ImageResource bottomOverBorder();

	@ImageOptions(repeatStyle = RepeatStyle.Horizontal)
	ImageResource bottomPressedBorder();

	@Override
	ImageResource bottomRightBorder();

	ImageResource bottomRightOverBorder();

	ImageResource bottomRightPressedBorder();
}
