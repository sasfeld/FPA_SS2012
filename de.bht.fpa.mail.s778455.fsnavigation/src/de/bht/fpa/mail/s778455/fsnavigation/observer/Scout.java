package de.bht.fpa.mail.s778455.fsnavigation.observer;

import java.util.Observable;

public final class Scout extends Observable {
  /**
   * Singleton-Pattern.
   */
  private static Scout instance = new Scout();

  /**
   * Singleton-Pattern. Create a new instance.
   */
  private Scout() {
    super();
  }

  /**
   * Singleton-Pattern. Return instance.
   * 
   * @return the current instance.
   */
  public static Scout getInstance() {
    return Scout.instance;
  }

  /**
   * The message which shall be sent to the observers.
   * 
   * @param o
   *          the message to send
   */
  public void newMessage(Object o) {
    // Pull-Request
    setChanged();
    this.notifyObservers(o);
  }

}
