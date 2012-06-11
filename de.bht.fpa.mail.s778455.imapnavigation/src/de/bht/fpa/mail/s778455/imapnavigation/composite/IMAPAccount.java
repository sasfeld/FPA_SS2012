package de.bht.fpa.mail.s778455.imapnavigation.composite;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;

public class IMAPAccount extends IMAPItem {

  private final Account account;

  public IMAPAccount(Account account) {
    super();
    this.account = account;
  }

  @Override
  public boolean hasChildren() {
    return true;
  }

  @Override
  public IMAPItem[] getChildren() {
    IMAPItem[] children = new IMAPItem[account.getFolders().size()];
    int ind = 0;
    for (Folder child : account.getFolders()) {
      children[ind] = new IMAPFolder(child);
      ++ind;
    }

    return children;
  }

  @Override
  public String toString() {
    return account.getName();
  }

}
