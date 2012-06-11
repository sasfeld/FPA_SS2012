package de.bht.fpa.mail.s778455.imapnavigation.composite;

import de.bht.fpa.mail.s000000.common.mail.model.Folder;

public class IMAPFolder extends IMAPItem {

  private final Folder folder;

  public IMAPFolder(Folder folder) {
    super();
    this.folder = folder;
  }

  @Override
  public boolean hasChildren() {
    if (folder.getFolders().size() != 0) {
      return true;
    }
    return false;
  }

  @Override
  public IMAPItem[] getChildren() {
    IMAPItem[] children = new IMAPItem[folder.getFolders().size()];
    int ind = 0;
    for (Folder child : folder.getFolders()) {
      children[ind] = new IMAPFolder(child);
      ++ind;
    }

    return children;
  }

  @Override
  public String toString() {
    return folder.getFullName();
  }

  public Folder returnMyFolder() {
    return this.folder;
  }

}
