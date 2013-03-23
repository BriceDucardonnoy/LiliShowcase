package com.brice.lili.showcase.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Picture implements Serializable {

	private static final long serialVersionUID = 4818208598134665653L;
    private ArrayList<Integer> categoryIds;
    private boolean isVisible;
    private HashMap<String, Comparable<?>> properties;

    public Picture() {
    	properties = new HashMap<String, Comparable<?>>();
    	categoryIds = new ArrayList<Integer>();
    	this.isVisible = true;
    }
    
    public Picture(String title) {
    	this();
    	properties.put("title", title);
    }
    
    public Picture(String title, String imageUrl, ArrayList<Integer> categoryIds, boolean isVisible) {
    	this(title);
    	properties.put("imageUrl", imageUrl);
        this.categoryIds = categoryIds;
        this.isVisible = isVisible;
    }

    public String getTitle() {
    	return properties.containsKey("title") ? (String) properties.get("title") : 
    		(properties.containsKey("Title") ? (String) properties.get("Title") : "");
    }
    
    public String getNameOrTitle() {
    	if(properties.containsKey("name")) return (String) properties.get("name");
    	if(properties.containsKey("Name")) return (String) properties.get("Name");
    	if(properties.containsKey("title")) return (String) properties.get("title");
    	if(properties.containsKey("Title")) return (String) properties.get("Title");
    	return "";
    }

    public String getImageUrl() {
    	return properties.containsKey("imageUrl") ? (String) properties.get("imageUrl") : "";
    }
    
    public ArrayList<Integer> getCategoryIds() {
    	return categoryIds;
    }
    
    public void setCategoryIds(ArrayList<Integer> categoryIds) {
    	this.categoryIds = categoryIds;
    }
    
    public void addCategoryId(Integer categoryId) {
    	if(!categoryIds.contains(categoryId)) {
    		categoryIds.add(categoryId);
    	}
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
