package de.bht.fpa.mail.s778455.fsnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s778455.fsnavigation.view.HistoryDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ViewHistoryHandler extends AbstractHandler {
  /**
   * The constructor.
   */
  public ViewHistoryHandler() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    Shell sh = window.getShell();

    // Show History Dialog
    showHistory(sh);
    return null;
  }

  /** Show history-Dialog here **/
  private void showHistory(Shell parentShell) {
    HistoryDialog hs = new HistoryDialog(parentShell);

    hs.open();

  }

}
