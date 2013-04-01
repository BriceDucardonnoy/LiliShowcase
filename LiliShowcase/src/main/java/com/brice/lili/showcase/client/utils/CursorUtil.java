package com.brice.lili.showcase.client.utils;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class CursorUtil {

	public static void showWaitCursor(Element e) {
		DOM.setStyleAttribute(e, "cursor", "wait");
	}

	public static void showDefaultCursor(Element e) {
		DOM.setStyleAttribute(e, "cursor", "default");
	}
	
}
