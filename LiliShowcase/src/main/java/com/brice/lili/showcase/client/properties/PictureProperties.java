package com.brice.lili.showcase.client.properties;

import com.brice.lili.showcase.shared.model.Picture;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface PictureProperties extends PropertyAccess<Picture> {
	
	@Path("imageUrl")
	ModelKeyProvider<Picture> key();
	ValueProvider<Picture, String> imageUrl();
	LabelProvider<Picture> title();
}
