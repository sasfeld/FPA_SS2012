package de.bht.fpa.mail.s778455.fsnavigation.pattern.composite;

import java.io.File;
import java.io.FileFilter;

/**
 * This class implements the FileFilter for our FSNavigation.
 * 
 * @author slash
 * 
 */
public final class FsFilter implements FileFilter {

  private static final FsFilter INSTANCE = new FsFilter();

  /**
   * Singleton-Pattern: create a new "hidden" instance
   */
  private FsFilter() {
    super();
  }

  public static FsFilter getInstance() {
    return FsFilter.INSTANCE;
  }

  @Override
  /**
   * The standard override accept-methode.
   * @return true if the file is a directory
   */
  public boolean accept(File file) {
    if (file.isDirectory()) {
      return true;
    }
    return false;
  }

  /**
   * The XML-Only-accept-method.
   * 
   * @param file
   *          - the file to check
   * @return true if the file ends with .xml
   */
  public boolean acceptXML(File file) {
    if (!file.isDirectory() && file.toString().endsWith(".xml")) {
      return true;
    }
    return false;
  }

}
