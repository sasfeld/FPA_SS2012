package de.bht.fpa.mail.s778455.fsnavigation.observer;

import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

/**
 * This class offers a store for Message-Collections, so that lately activated
 * plugins are able to access those.
 * 
 * @author Sascha Feldmann
 * 
 */
public final class MessageStore implements Observer {
  private static final MessageStore INSTANCE = new MessageStore();
  private Collection<?> messages;
  private boolean upd = true;

  private MessageStore() {
  }

  /**
   * SINGLETON!!!!
   * 
   * @return the only instance
   */
  public static MessageStore getInstance() {
    return MessageStore.INSTANCE;
  }

  /**
   * Return the collection of input messages.
   * 
   * @return a Collection of Message-Objects.
   */
  public Collection<?> returnMessages() {
    return messages;
  }

  /**
   * Set Update mode
   * 
   * @param upd
   */
  public void setUpdate(boolean upd) {
    this.upd = upd;
  }

  @Override
  public void update(Observable o, Object obj) {
    if (this.upd && obj instanceof Collection<?>) {
      messages = (Collection<?>) obj;
    }
  }

}
