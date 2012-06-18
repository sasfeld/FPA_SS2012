package de.bht.fpa.mail.s778455.imapnavigation.composite;

/**
 * The abstract component class. - transparency
 * 
 * @author Sascha Feldmann
 * 
 */
public abstract class IMAPItem {

  /**
   * Check if the element has children.
   * 
   * @return true if the element has children.
   */
  public abstract boolean hasChildren();

  /**
   * Return an array of children.
   * 
   * @return an array of children.
   */
  public abstract IMAPItem[] getChildren();
}
