package de.bht.fpa.mail.s778455.imapnavigation.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s778455.imapnavigation.Activator;
import de.bht.fpa.mail.s778455.imapnavigation.composite.IMAPAccount;
import de.bht.fpa.mail.s778455.imapnavigation.composite.IMAPItem;

public class ViewLabelProvider extends LabelProvider {

  /**
   * The path to the file icon.
   */
  private static final String ICON_ACCOUNT = "icons/Crystal_Clear_app_email.png";
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
   * @throws IllegalArgumentException if the element is not subObject of IMAPItem.
   */
  public Image getImage(Object element) {
    Image img;
    if (element instanceof IMAPItem) {
      if (element instanceof IMAPAccount) {
        img = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, ICON_ACCOUNT).createImage();
      } else { // load the file icon
        img = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, ICON_FOLDER).createImage();
      }
    } else {
      throw new IllegalArgumentException("The element must be an IMAPItem.");
    }
    return img;
  }
}
