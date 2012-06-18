package de.bht.fpa.mail.s778455.imapnavigation.views;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.builder.AccountBuilder;
import de.bht.fpa.mail.s000000.common.mail.model.builder.Builders;
import de.bht.fpa.mail.s000000.common.rcp.selection.SelectionHelper;
import de.bht.fpa.mail.s778455.imapnavigation.composite.IMAPDummyContainer;
import de.bht.fpa.mail.s778455.imapnavigation.composite.IMAPFolder;
import de.bht.fpa.mail.s778455.imapnavigation.observer.Scout;
import de.bht.fpa.mail.s778455.maillist.views.IMailListViewAccess;
import de.bht.fpa.mail.s778455.maillist.views.MailListView;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class IMAPView extends ViewPart implements Observer {

  /**
   * The ID of the view as specified by the extension.
   */
  public static final String ID = "de.bht.fpa.mail.s778455.imapnavigation.views.SampleView";

  private TreeViewer viewer;

  /**
   * This listener reacts on a changed input of the IMAP view.
   * 
   * @author Sascha Feldmann
   * 
   */
  public class FolderSelectedListener implements ISelectionChangedListener {

    @Override
    public void selectionChanged(SelectionChangedEvent event) {
      final IWorkbench workbench = PlatformUI.getWorkbench();
      // Unwrap as Folder

      IMAPFolder folder = SelectionHelper.handleStructuredSelectionEvent(event, IMAPFolder.class);

      if (folder != null) {
        getViewSite().getActionBars().getStatusLineManager()
            .setMessage(folder.returnMyFolder().getFullName() + " was selected");
        // Inform Maillist
        final IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
        final IViewPart view = page.findView(MailListView.ID);

        IMailListViewAccess mailView = (MailListView) view;
        mailView.update(null, folder.returnMyFolder().getMessages());

      }
    }
  }

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  @Override
  public void createPartControl(Composite parent) {
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    // }
    // We set the ContentProvider for the TreeViewer. This class prepares data
    // to be shown by the TreeViewer.
    viewer.setContentProvider(new ViewContentProvider());

    // We set the LabelProvider. This class decides how elements in the tree are
    // shown by returning a text and an optional icon.
    // viewer.setLabelProvider(new ViewLabelProvider());
    viewer.setLabelProvider(new ViewLabelProvider());
    // Here we set the root of the tree. The framework will ask for more data
    // when the user expands tree items.
    viewer.setInput(createModel());

    // Create the help context id for the viewer's control
    PlatformUI.getWorkbench().getHelpSystem()
        .setHelp(viewer.getControl(), "de.bht.fpa.mail.s778455.imapnavigation.viewer");

    // Add listener
    viewer.addSelectionChangedListener(new FolderSelectedListener());

    Scout.getInstance().addObserver(this);
  }

  private Object createModel() {
    IMAPDummyContainer container = IMAPDummyContainer.getInstance();
    container.addAccount(getAccount());
    return IMAPDummyContainer.getInstance();
  }

  /**
   * Return an account instance.
   */
  private Account getAccount() {
    // @formatter:off
    AccountBuilder builder = Builders.newAccountBuilder();
    builder
        .host("imap.gmail.com")
        .username("bhtfpa@googlemail.com")
        .password("B-BgxkT_anr2bubbyTLM")
        .name("BOB-Imap");
    // @formatter:on
    return builder.build();
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }

  @Override
  public void update(Observable o, Object arg) {
    viewer.refresh();
  }
}