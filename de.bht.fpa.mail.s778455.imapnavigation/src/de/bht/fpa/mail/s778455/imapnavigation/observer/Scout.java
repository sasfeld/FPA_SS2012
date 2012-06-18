package de.bht.fpa.mail.s778455.imapnavigation.observer;

import java.util.Observable;

/**
 * Observer-Pattern: the Scout-class
 * 
 * @author Sascha Feldmann
 * 
 */
public class Scout extends Observable {
  private static Scout instance;

  /**
   * @return the only existing instance
   */
  public static Scout getInstance() {
    if (instance == null) {
      instance = new Scout();
    }
    return instance;
  }

  /**
   * Do a push.
   */
  public void sendMessage() {
    setChanged();
    this.notifyObservers();
  }
}
