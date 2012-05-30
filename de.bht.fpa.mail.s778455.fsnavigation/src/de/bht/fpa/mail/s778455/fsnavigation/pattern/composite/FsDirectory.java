package de.bht.fpa.mail.s778455.fsnavigation.pattern.composite;

import java.io.File;

/**
 * Composite - Pattern: This class modelles the abstract Compositum class. See
 * also the abstract {@link FileSystemItem} class which realizes the Component
 * and the {@link FsFile} class which realizes a leaf.
 * 
 * @author Sascha Feldmann
 * 
 */
public class FsDirectory extends FileSystemItem {
  /**
   * Create a new FsDirectory-Object which really contains a directory and not a
   * file.
   * 
   * @param file
   *          - a File instance
   * @throws IllegalArgumentException
   *           if the client tries to create a leaf (a file in this case which
   *           is not a directory).
   */
  public FsDirectory(File file) {
    super(file);
    if (!file.isDirectory()) {
      throw new IllegalArgumentException("The reference for file in FsDirectory must be a directory.");
    }
  }

  /**
   * Return all the children of the current compositum and create a new
   * FileSystemItem (component) for each.
   * 
   * @return An array of FileSystemItems (Component instances)
   */
  @Override
  public FileSystemItem[] getFiles() {
    int ind = 0;
    FileSystemItem[] children = new FileSystemItem[myFile.listFiles(FsFilter.getInstance()).length];
    for (File child : myFile.listFiles(FsFilter.getInstance())) {
      if (child.isDirectory()) {
        children[ind] = new FsDirectory(child);
      }
      ind++;
    }

    // Convert to Array again

    return children;
  }

  // @Override
  // public boolean hasChildren() {
  // if (myFile.list().length == 0) {
  // return false;
  // }
  // return true;
  // }
}
