package de.bht.fpa.mail.s778455.fsnavigation.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.xml.bind.JAXB;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.rcp.selection.SelectionHelper;
import de.bht.fpa.mail.s778455.fsnavigation.observer.Scout;
import de.bht.fpa.mail.s778455.fsnavigation.pattern.composite.FsDirectory;
import de.bht.fpa.mail.s778455.fsnavigation.pattern.composite.FsFilter;
import de.bht.fpa.mail.s778455.maillist.views.MailListView;

/**
 * Modelles the Navigation view.
 * 
 * @author Sascha Feldmann
 * 
 */
public class NavigationView extends ViewPart implements Observer {
  /**
   * The Navigation View's ID (so that it can be found).
   */
  public static final String ID = "de.bht.fpa.s778455.fsnavigation.NavigationView";
  /**
   * Path to the History - SaveFile.
   */
  public static final String PATH_HISTORYFILE = "directoryhistory.log";
  /**
   * Standard message if there aren't any directories saved.
   */
  public static final String MSG_HISTORY_EMPTY = "No base directories in history.";
  private TreeViewer viewer;

  /**
   * The listener reacts on changed selections of the navigation view.
   * 
   * @author Sascha Feldmann
   * 
   */
  public class DirSelectedListener implements ISelectionChangedListener {

    @Override
    /** This method is called when the viewer's input gets changed 
     * @param event the SelectionChangedEvent **/
    public void selectionChanged(SelectionChangedEvent event) {
      // Unwrap as Directory
      FsDirectory dir = SelectionHelper.handleStructuredSelectionEvent(event, FsDirectory.class);
      getViewSite().getActionBars().getStatusLineManager().setMessage(dir.getMyFile().getPath() + " was selected");

      // Count elements

      File[] children = dir.getMyFile().listFiles();
      ArrayList<File> leafs = new ArrayList<File>();
      @SuppressWarnings("unused")
      int count = 0;
      for (File child : children) {
        if (FsFilter.getInstance().acceptXML(child)) {
          count++;
          leafs.add(child);
        }
      }

      // System.out.println("Number of messages: " + count);

      ArrayList<Message> messages = new ArrayList<Message>();
      for (File xmlFile : leafs) {
        Message message = JAXB.unmarshal(xmlFile, Message.class);
        if (message.getId() != null) { // check if valid Message-XML
          messages.add(message);
        }
      }
      // Inform Maillist
      final IWorkbench workbench = PlatformUI.getWorkbench();
      final IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
      final IViewPart view = page.findView(MailListView.ID);

      MailListView mailView = (MailListView) view;
      mailView.update(null, messages);
    }
  }

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  @Override
  public void createPartControl(Composite parent) {
    // a TreeViewer is a Jface widget, which shows trees
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    // }
    // We set the ContentProvider for the TreeViewer. This class prepares data
    // to be shown by the TreeViewer.
    viewer.setContentProvider(new ViewContentProvider());

    // We set the LabelProvider. This class decides how elements in the tree are
    // shown by returning a text and an optional icon.
    viewer.setLabelProvider(new ViewLabelProvider());

    // Here we set the root of the tree. The framework will ask for more data
    // when the user expands tree items.
    viewer.setInput(createModel());

    // Add the NavigationView as observer
    Scout.getInstance().addObserver(this);

    // Add selection change listener here
    viewer.addSelectionChangedListener(new DirSelectedListener());

    // load last base directory
    loadLastDir();

    // viewer.ge
  }

  /**
   * Load the last directory from the history dialog.
   */
  private void loadLastDir() {
    ArrayList<String> hist = HistoryDialog.loadHistory();
    if (hist != null) {
      String lastFile = hist.get(hist.size() - 1);
      File f = new File(lastFile);

      Scout.getInstance().newMessage(f);
    } else {
      System.out.println("Last f missing");
    }
  }

  @Override
  public void saveState(IMemento memento) {
    System.out.println("SaveState");
  }

  /**
   * We will set up a model to initialize tree hierarchy.
   */
  private Object createModel() {
    // We create a new FsDirectory object which contains the root (user home) -
    // directory using the SYSTEM VARIABLES
    return new FsDirectory(new File(System.getProperty("user.home")));
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }

  @Override
  public void update(Observable scout, Object obj) {
    if (obj instanceof File) {
      viewer.setInput(new FsDirectory((File) obj));
    }
  }
}