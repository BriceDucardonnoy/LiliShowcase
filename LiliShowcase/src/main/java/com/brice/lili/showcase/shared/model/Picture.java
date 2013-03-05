/**
 * Copyright (c) 2011 Canoo Engineering AG info@canoo.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.brice.lili.showcase.shared.model;

import java.io.Serializable;
import java.util.HashMap;

public class Picture implements Serializable {

	private static final long serialVersionUID = 4818208598134665653L;
    private int[] categoryIds;
    private boolean isVisible;
    private HashMap<String, Object> properties;

    public Picture(String name, String imageUrl, int[] categoryIds, boolean isVisible) {
    	properties = new HashMap<String, Object>();
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

	public HashMap<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, Object> properties) {
		this.properties = properties;
	}
	
	public Object getProperty(String property) {
		return properties.get(property);
	}
	
	public Object addProperty(String property, Object value) {
		return properties.put(property, value);
	}
	
	public Object removeProperty(String property) {
		return properties.remove(property);
	}
}
