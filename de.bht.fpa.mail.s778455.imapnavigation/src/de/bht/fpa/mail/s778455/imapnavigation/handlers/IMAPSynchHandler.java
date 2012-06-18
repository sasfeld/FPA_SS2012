package de.bht.fpa.mail.s778455.imapnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import de.bht.fpa.mail.s778455.imapnavigation.composite.IMAPDummyContainer;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class IMAPSynchHandler extends AbstractHandler {

  /**
   * The constructor.
   */
  public IMAPSynchHandler() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    handleJob();
    return null;
  }

  /**
   * Handle Job here.
   */
  private void handleJob() {
    IMAPDummyContainer.getInstance().synchronize();

  }
}
