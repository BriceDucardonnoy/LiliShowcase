package com.brice.lili.showcase.client.properties;

import com.brice.lili.showcase.shared.model.Category;
import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface CategoryProperties extends PropertyAccess<Category> {

	@Path("id")
	ModelKeyProvider<Category> key();
	
	ValueProvider<Category, Integer> id();
	ValueProvider<Category, String> name();
}
