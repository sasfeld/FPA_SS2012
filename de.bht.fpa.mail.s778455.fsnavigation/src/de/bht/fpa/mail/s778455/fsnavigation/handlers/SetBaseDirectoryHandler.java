package de.bht.fpa.mail.s778455.fsnavigation.handlers;

import java.io.File;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s778455.fsnavigation.observer.Scout;
import de.bht.fpa.mail.s778455.fsnavigation.view.HistoryDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SetBaseDirectoryHandler extends AbstractHandler {
  /**
   * The constructor.
   */
  public SetBaseDirectoryHandler() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    Shell sh = window.getShell();

    // Show File Dialog and return File which must be a directory
    File choosed = getChoosenFile(sh);
    if (choosed != null && choosed.isDirectory()) {
      Scout.getInstance().newMessage(choosed);
      HistoryDialog.saveHistory(choosed);
    }
    return null;
  }

  /**
   * Show a JFace Directoy dialog
   * 
   * @param sh
   *          - the container shell
   * @return the chosen directory
   */
  private File getChoosenFile(Shell sh) {
    DirectoryDialog dg = new DirectoryDialog(sh);
    String path = dg.open();

    if (path != null) {
      return new File(path);
    }
    return null;

  }
}
