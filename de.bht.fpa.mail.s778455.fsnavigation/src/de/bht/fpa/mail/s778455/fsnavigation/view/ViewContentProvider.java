package de.bht.fpa.mail.s778455.fsnavigation.view;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.bht.fpa.mail.s778455.fsnavigation.pattern.composite.FileSystemItem;

/**
 * This class realizes the navigation view's content which is organized by the
 * Composite Pattern.
 * 
 * @author Sascha Feldmann
 * 
 */
public class ViewContentProvider implements ITreeContentProvider {

  /**
   * This method is called when the user expands a tree node in the View. The
   * parameter of the method is the selected item, and the method is expected to
   * return the direct children of the item.
   * 
   * @param parentElement
   *          the expanded element in the tree, for which the framework requests
   *          the children
   * @throws IllegalArgumentException
   *           if the parentElement is not subObject of FileSystemItem.
   */
  @Override
  public Object[] getChildren(Object parentElement) {
    // Let's cast the parentElement to FileSystemItem and let the element (which
    // has to be a directory -> controlled by framework) return all children
    if (parentElement instanceof FileSystemItem) {
      return ((FileSystemItem) parentElement).getFiles();
    } else {
      throw new IllegalArgumentException("The element must be an FileSystemItem instance.");
    }
  }

  /**
   * This method is called when the user expands a tree node in the View. The
   * framework asks is the given element has any children. The parameter of the
   * method is a tree item, and the method is expected to return
   * <code>true></code> if the item has children, or <code>false</code> if it
   * has no children.
   * 
   * @param element
   *          a tree item, for which the framework wants to know if it has
   *          children
   * @throws IllegalArgumentException
   *           if the parentElement is not subObject of TFileSystemItem.
   */
  @Override
  public boolean hasChildren(Object element) {
    // We check if the current FileSystemObject returns a file that is a
    // directory
    if (element instanceof FileSystemItem) {
      return ((FileSystemItem) element).hasChildren();
    } else {
      throw new IllegalArgumentException("The element must be an FileSystemItem instance.");
    }
  }

  // ==========================================================================
  // In our simple tree, you don't need to change any of the following methods.
  // ==========================================================================

  @Override
  public Object[] getElements(Object parent) {
    return getChildren(parent);
  }

  @Override
  /**
   * Every time the view changes this method will be called.
   */
  public void inputChanged(Viewer v, Object oldInput, Object newInput) {
    // saveViewer(v); doesn't work
  }

  @Override
  public void dispose() {
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }
}