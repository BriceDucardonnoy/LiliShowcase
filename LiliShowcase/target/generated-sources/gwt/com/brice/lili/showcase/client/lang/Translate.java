package com.brice.lili.showcase.client.lang;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/brice/workspace/LiliShowcase/target/LiliShowcase-0.0.1-SNAPSHOT/WEB-INF/classes/com/brice/lili/showcase/client/lang/Translate.properties'.
 */
public interface Translate extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Enter your name".
   * 
   * @return translated "Enter your name"
   */
  @DefaultMessage("Enter your name")
  @Key("nameField")
  String nameField();

  /**
   * Translated "Send".
   * 
   * @return translated "Send"
   */
  @DefaultMessage("Send")
  @Key("sendButton")
  String sendButton();
}
