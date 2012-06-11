package de.bht.fpa.mail.s778455.imapnavigation.composite;

public abstract class IMAPItem {

  public IMAPItem() {

  }

  public abstract boolean hasChildren();

  public abstract IMAPItem[] getChildren();
}
