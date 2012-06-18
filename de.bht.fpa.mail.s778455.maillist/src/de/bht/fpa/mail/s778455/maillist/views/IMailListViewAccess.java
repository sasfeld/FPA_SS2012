package de.bht.fpa.mail.s778455.maillist.views;

import java.util.Collection;
import java.util.Observable;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Offer access to the MailListView's input.
 * 
 * @author Sascha Feldmann
 * 
 */
public interface IMailListViewAccess {

  /**
   * Return a collection of messages (the MailListView's input)
   * 
   * @return the collection of Message objects.
   */
  Collection<Message> getMessages();

  /**
   * Do an update / intersection with Observer-interface.
   * 
   * @param arg0
   *          - the scout instance
   * @param obj
   *          - the transmitted object
   */
  void update(Observable arg0, Object obj);
}
