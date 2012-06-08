package de.bht.fpa.mail.s778455.filter.handlers;

import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s000000.common.filter.FilterDialog;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s778455.filter.decorator.MessageFilter;
import de.bht.fpa.mail.s778455.filter.parser.FilterCommandParser;
import de.bht.fpa.mail.s778455.filter.parser.ParserCommandBuilder;
import de.bht.fpa.mail.s778455.fsnavigation.observer.MessageStore;
import de.bht.fpa.mail.s778455.fsnavigation.observer.Scout;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class filterHandler extends AbstractHandler implements Observer {

  /**
   * The constructor.
   */
  public filterHandler() {
    Scout.getInstance().addObserver(this);
  }

  /**
   * Uses the command pattern. the command has been executed, so extract extract
   * the needed information from the application context.
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    Shell sh = window.getShell();

    // Configure-Dialog
    if (event.getCommand().getId().equals("de.bht.fpa.mail.s778455.filter.commands.configureCommand")) {
      FilterDialog fd = new FilterDialog(sh);
      if (fd.open() == FilterDialog.OK) {
        return filterMessages(fd);
      }
      // Clear-Command
    } else if (event.getCommand().getId().equals("de.bht.fpa.mail.s778455.filter.clearCommand")) {
      return MessageStore.getInstance().returnMessages();
    }
    return null;

  }

  /**
   * Filter the selected messages
   * 
   * @param fd
   *          - the FilterDialog where the user can do some operations
   * @return
   */
  private Collection<?> filterMessages(FilterDialog fd) {
    // Take messages from Store
    Collection<?> messagesToFilter = MessageStore.getInstance().returnMessages();
    if (messagesToFilter == null) {
      showMessage(fd.getShell(), "No messages selected!");
    } else if (messagesToFilter.size() == 0) {
      showMessage(fd.getShell(), "No messages available!");
    } else {
      // Filter here
      String command = ParserCommandBuilder.buildCommand(fd);
      MessageFilter resultFilter = FilterCommandParser.getInstance().parseCommand(command);
      @SuppressWarnings("unchecked")
      Iterable<Message> messagesToFilter2 = (Iterable<Message>) messagesToFilter;
      Collection<?> filterMessages = resultFilter.filter(messagesToFilter2);
      return filterMessages;
    }

    return null;
  }

  /**
   * Generate a message dialog to inform the user.
   * 
   * @param shell
   * 
   * @param string
   *          - the message to be showed
   */
  private void showMessage(Shell shell, String message) {
    MessageDialog.openInformation(shell, "Advice", message);
  }

  @Override
  public void update(Observable arg0, Object obj) {
    // TODO Auto-generated method stub

  }
}
