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

public class Person implements Serializable {

	private static final long serialVersionUID = 4818208598134665653L;
	private final String fName;
    private final String fImageUrl;
    private int[] categoryIds;

    public Person(String name, String imageUrl, int[] categoryIds) {
        fName = name;
        fImageUrl = imageUrl;
        this.categoryIds = categoryIds;
    }

    public String getName() {
        return fName;
    }

    public String getImageUrl() {
        return fImageUrl;
    }
    
    public int[] getCategoryIds() {
    	return categoryIds;
    }
}
