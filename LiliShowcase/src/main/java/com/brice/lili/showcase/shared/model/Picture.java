package com.brice.lili.showcase.shared.model;

import java.io.Serializable;
import java.util.HashMap;

public class Picture implements Serializable {

	private static final long serialVersionUID = 4818208598134665653L;
    private int[] categoryIds;
    private boolean isVisible;
    private HashMap<String, Comparable<?>> properties;

    public Picture(String name, String imageUrl, int[] categoryIds, boolean isVisible) {
    	properties = new HashMap<String, Comparable<?>>();
    	properties.put("name", name);
    	properties.put("imageUrl", imageUrl);
        this.categoryIds = categoryIds;
        this.isVisible = isVisible;
    }

    public String getName() {
    	return properties.containsKey("name") ? (String) properties.get("name") : "";
    }

    public String getImageUrl() {
    	return properties.containsKey("imageUrl") ? (String) properties.get("imageUrl") : "";
    }
    
    public int[] getCategoryIds() {
    	return categoryIds;
    }

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public HashMap<String, Comparable<?>> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, Comparable<?>> properties) {
		this.properties = properties;
	}
	
	public Comparable<?> getProperty(String property) {
		return properties.get(property);
	}
	
	public Comparable<?> addProperty(String property, Comparable<?> value) {
		return properties.put(property, value);
	}
	
	public Comparable<?> removeProperty(String property) {
		return properties.remove(property);
	}
}
