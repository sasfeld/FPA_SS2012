package de.bht.fpa.mail.s778455.maillist.listener;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;

import de.bht.fpa.mail.s778455.fsnavigation.observer.MessageStore;
import de.bht.fpa.mail.s778455.fsnavigation.observer.Scout;

public class FilterExecutionListener implements IExecutionListener {
  @Override
  public void notHandled(String commandId, NotHandledException exception) {
    // not important for us - positive action will get handled only
  }

  @Override
  public void postExecuteFailure(String commandId, ExecutionException exception) {
    // not important for us
  }

  @Override
  public void postExecuteSuccess(String commandId, Object returnValue) {
    if (returnValue != null) {
      // MessageStore shall not be updated
      MessageStore.getInstance().setUpdate(false);

      Scout.getInstance().newMessage(returnValue);
      MessageStore.getInstance().setUpdate(true);
    }
  }

  @Override
  public void preExecute(String commandId, ExecutionEvent event) {
    // not important for us
  }

}
