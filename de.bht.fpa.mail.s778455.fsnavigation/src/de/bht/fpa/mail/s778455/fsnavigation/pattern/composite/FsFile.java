package de.bht.fpa.mail.s778455.fsnavigation.pattern.composite;

import java.io.File;

/**
 * Composite - Pattern: This class modelles the leaf class. See also the
 * {@link FsDirectory} class which realizes a Compositum and the
 * {@link FileSystemItem} class which realizes the abstract Component.
 * 
 * @author Sascha Feldmann
 * 
 */
public class FsFile extends FileSystemItem {

  /**
   * Create a new FsFile-Object which really contains a file and not a
   * directory.
   * 
   * @param file
   *          - a File instance
   * @throws IllegalArgumentException
   *           if the client tries to create a compositum (a directory in this
   *           case).
   */
  public FsFile(File file) {
    super(file);
    if (file.isDirectory()) {
      throw new IllegalArgumentException("The reference for file in FsFile mustn't be a directory.");
    }
  }

  /**
   * This is just a dummy method that is caused by the Composite Pattern: this
   * method has to be implemented because the Composite FileSystemItem class has
   * to declare it abstract. -> "Transparency Variant" of the GOF implementation
   * of the Composite Pattern
   * 
   * @return null
   */
  @Override
  public FileSystemItem[] getFiles() {
    // Return null because a file has no children
    return null;
  }
}
