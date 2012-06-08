package de.bht.fpa.mail.s778455.fsnavigation.pattern.composite;

import java.io.File;
import java.io.FileFilter;

/**
 * Composite - Pattern: This class modelles the abstract Component class. See
 * also the {@link FsDirectory} class which realizes a Compositum and the
 * {@link FsFile} class which realizes a leaf.
 * 
 * This composite-pattern uses the transparency model.
 * 
 * @author Sascha Feldmann
 * 
 */
public abstract class FileSystemItem {

  /**
   * The file refernce for the current FileSystemItem which is final.
   */
  protected final File myFile;

  /**
   * Create a new FileSystem Item.
   * 
   * @param file
   *          - a file instance
   */
  public FileSystemItem(File file) {
    if (file == null) {
      throw new IllegalArgumentException("File missing!");
    }
    this.myFile = file;
  }

  // public abstract void addFile(File file);

  /**
   * Return all children
   * 
   * @return an array of children
   */
  public abstract FileSystemItem[] getFiles();

  /**
   * Check whether the element has children. Should count children
   * 
   * @return true if the element has children.
   */
  public boolean hasChildren() {
    File[] dirs = myFile.listFiles(new FileFilter() {

      @Override
      public boolean accept(File name) {
        // TODO Auto-generated method stub
        return name.isDirectory();
      }
    });
    if (dirs == null || dirs.length == 0) {
      return false;
    }
    return true;
  }

  /**
   * Get the file instance for the current FileSystemItem. The client should not
   * know about the inner file instance!
   * 
   * @return the File instance.
   */
  public File getMyFile() {
    return this.myFile;
  }

  @Override
  /**
   * Get the file name for the current file instance.
   * @return a String which contains the file name.
   */
  public String toString() {
    return myFile.getName();
  }

}
