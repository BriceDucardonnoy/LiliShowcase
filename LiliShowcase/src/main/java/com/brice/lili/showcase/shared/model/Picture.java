package com.brice.lili.showcase.shared.model;

import java.io.Serializable;
import java.util.HashMap;

public class Picture implements Serializable {

	private static final long serialVersionUID = 4818208598134665653L;
    private Integer[] categoryIds;
    private boolean isVisible;
    private HashMap<String, Comparable<?>> properties;

    public Picture() {
    	properties = new HashMap<String, Comparable<?>>();
    	this.isVisible = true;
    }
    
    public Picture(String title) {
    	this();
    	properties.put("title", title);
    }
    
    public Picture(String title, String imageUrl, Integer[] categoryIds, boolean isVisible) {
    	this(title);
    	properties.put("imageUrl", imageUrl);
        this.categoryIds = categoryIds;
        this.isVisible = isVisible;
    }

    public String getTitle() {
    	return properties.containsKey("title") ? (String) properties.get("title") : 
    		(properties.containsKey("Title") ? (String) properties.get("Title") : "");
    }

    public String getImageUrl() {
    	return properties.containsKey("imageUrl") ? (String) properties.get("imageUrl") : "";
    }
    
    public Integer[] getCategoryIds() {
    	return categoryIds;
    }
    
    public void setCategoryIds(Integer []categoryIds) {
    	this.categoryIds = categoryIds;
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
