package de.bht.fpa.mail.s778455.fsnavigation.view;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s778455.fsnavigation.Activator;
import de.bht.fpa.mail.s778455.fsnavigation.pattern.composite.FileSystemItem;

public class ViewLabelProvider extends LabelProvider {

  /**
   * The path to the file icon.
   */
  private static final String ICON_LEAF_FILE = "icons/Crystal_Clear_filesystem_blockdevice.png";
  /**
   * The path to the folder icon.
   */
  private static final String ICON_FOLDER = "icons/Crystal_Clear_filesystem_folder_yellow.png";

  @Override
  public String getText(Object element) {
    // return file name
    return super.getText(element);
  }

  @Override
  /**
   * Return the icon for the current element.
   * @return an image object of the icon.
   * @throws IllegalArgumentException if the element is not subObject of FileSystemItem.
   */
  public Image getImage(Object element) {
    Image img;
    if (element instanceof FileSystemItem) {
      // Seperate between directories and "leaf files"
      if (((FileSystemItem) element).hasChildren()) {
        // load the directory icon
        img = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, ICON_FOLDER).createImage();
      } else { // load the file icon
        img = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, ICON_LEAF_FILE).createImage();
      }
    } else {
      throw new IllegalArgumentException("The element must be an FileSystemItem instance to match an icon.");
    }
    return img;
  }
}
