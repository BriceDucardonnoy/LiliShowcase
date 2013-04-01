package com.brice.lili.showcase.client.utils;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Utils {

	/**
	 * Cursor
	 */
	public static void showWaitCursor(Element e) {
		DOM.setStyleAttribute(e, "cursor", "wait");
	}

	public static void showDefaultCursor(Element e) {
		DOM.setStyleAttribute(e, "cursor", "default");
	}

	public static void loadFile(final AsyncCallback<String> callback, final String filename) {
		if(filename == null || filename.isEmpty()) {
			return;
		}
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, filename);
        try {
            requestBuilder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    if(callback != null) callback.onFailure(exception);
                }
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if(callback != null) callback.onSuccess(response.getText());
                }
            });
        } catch (RequestException e) {
            Log.error("failed file reading: " + e.getMessage());
        }
    }
	
	public static void switchLocale(String locale) {
		UrlBuilder builder = Location.createUrlBuilder().setParameter("locale", locale);
		String newUrl = builder.buildString();
		// Lame hack for debug execution only
		String debugParam = Location.getParameter("gwt.codesvr");
		if(debugParam != null && !debugParam.isEmpty()) {
			newUrl = newUrl.replace("gwt.codesvr=127.0.1.1%3A", "gwt.codesvr=127.0.1.1:");
		}
		Window.Location.replace(newUrl);
	}
	
}
