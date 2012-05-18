package de.bht.fpa.mail.s778455.maillist.views;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.mail.model.Sender;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s000000.common.table.MessageValues;
import de.bht.fpa.mail.s778455.fsnavigation.observer.Scout;
import de.bht.fpa.mail.s778455.maillist.Activator;
import de.ralfebert.rcputils.tables.ColumnBuilder;
import de.ralfebert.rcputils.tables.ICellFormatter;
import de.ralfebert.rcputils.tables.TableViewerBuilder;
import de.ralfebert.rcputils.tables.format.Formatter;
import de.ralfebert.rcputils.tables.format.StringValueFormatter;

public class MailListView extends ViewPart implements Observer {

  /**
   * The importance column width - integer (percent)
   */
  private static final int COLUMN_WIDTH_IMPORTANCE = 10;
  /**
   * The received column width - integer (percent)
   */
  private static final int COLUMN_WIDTH_RECEIVED = 10;
  /**
   * The read column width - integer (percent)
   */
  private static final int COLUMN_WIDTH_READ = 10;
  /**
   * The sender column width - integer (percent)
   */
  private static final int COLUMN_WIDTH_SENDER = 20;
  /**
   * The recipients column width - integer (percent)
   */
  private static final int COLUMN_WIDTH_RECIPIENTS = 20;
  /**
   * The sender column width - integer (percent)
   */
  private static final int COLUMN_WIDTH_SUBJECT = 20;
  /**
   * The path to the file icon: normal priority.
   */
  private static final String ICON_IMPORTANCE_NORMAL = "icons/arrow_normal.gif";
  /**
   * The path to the file icon: high priority.
   */
  private static final String ICON_IMPORTANCE_HIGH = "icons/arrow_high.gif";
  /**
   * The path to the file icon: low priority.
   */
  private static final String ICON_IMPORTANCE_LOW = "icons/arrow_low.gif";
  private TableViewer tableViewer;

  @Override
  public void createPartControl(Composite parent) {

    parent.setLayout(new GridLayout(1, false));

    Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout(3, false));
    composite.setLayoutData(GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).create());

    new org.eclipse.swt.widgets.Label(composite, SWT.NONE).setText("Suchbegriff: ");
    final Text text = new Text(composite, SWT.BORDER);
    text.setLayoutData(GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).create());
    text.setText("eMails durchsuchen");

    Composite tableComposite = new Composite(parent, SWT.FILL_WINDING);
    tableComposite.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL
        | GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));

    final TableViewerBuilder t = new TableViewerBuilder(tableComposite);
    // tableComposite.setLayoutData(GridDataFactory.swtDefaults().grab(true,
    // false).align(SWT.FILL, SWT.BEGINNING)
    // .create());

    text.addKeyListener(new KeyListener() {

      @Override
      public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

      }

      @Override
      public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

        ViewerFilter filter = new ViewerFilter() {

          @Override
          public boolean select(Viewer viewer, Object parentElement, Object element) {
            // TODO Auto-generated method stub
            if (element instanceof Message) {
              String search = text.getText();
              Message msg = (Message) element;
              if (msg.getSubject().contains(search) || msg.getText().contains(search)
                  || msg.getReceived().toString().contains(search) || msg.getSent().toString().contains(search)
                  || msg.getSender().getEmail().contains(search) || msg.getSender().getPersonal().contains(search)) {
                return true;
              }
              for (Recipient recipient : msg.getRecipients()) {
                if (recipient.getEmail().contains(search) || recipient.getPersonal().contains(search)) {
                  return true;
                }
              }
            }
            return false;
          }
        };
        t.getTableViewer().addFilter(filter);
      }
    });

    // composite.setLayout(new GridLayout());
    // Add TextField here

    // searchField.addPropertyChangeListener(new PropertyChangeListener() {
    //
    // @Override
    // public void propertyChange(PropertyChangeEvent arg0) {
    // // TODO Auto-generated method stub
    // System.out.println("Test");
    // }
    // });

    // parent.getDisplay().
    // Define columns here

    ColumnBuilder importance = t.createColumn("Importance");
    importance.bindToValue(MessageValues.IMPORTANCE);
    importance.setPercentWidth(COLUMN_WIDTH_IMPORTANCE);
    importance.format(new ICellFormatter() {
      /**
       * Show icons instead of text
       */
      @Override
      public void formatCell(ViewerCell cell, Object value) {
        cell.setText(""); // Hide text
        if (value.equals(Importance.LOW)) {
          cell.setImage(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, ICON_IMPORTANCE_LOW).createImage());
        } else if (value.equals(Importance.NORMAL)) {
          cell.setImage(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, ICON_IMPORTANCE_NORMAL).createImage());
        } else if (value.equals(Importance.HIGH)) {
          cell.setImage(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, ICON_IMPORTANCE_HIGH).createImage());
        }
      }
    });
    importance.build();

    // Date recieved
    ColumnBuilder received = t.createColumn("Received");
    received.bindToValue(MessageValues.RECEIVED);
    received.setPercentWidth(COLUMN_WIDTH_RECEIVED);
    received.useAsDefaultSortColumn(); // Use date as standard sort
    StringValueFormatter dateFormat = Formatter.forDate(SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM));
    received.format(dateFormat);
    received.build();

    ColumnBuilder read = t.createColumn("Read");
    read.bindToValue(MessageValues.READ);
    read.setPercentWidth(COLUMN_WIDTH_READ);
    read.build();

    ColumnBuilder sender = t.createColumn("Sender");
    sender.bindToValue(MessageValues.SENDER);
    sender.setPercentWidth(COLUMN_WIDTH_SENDER);
    sender.format(new ICellFormatter() {
      /**
       * Show sender's eMail
       */
      @Override
      public void formatCell(ViewerCell cell, Object value) {
        Sender msg = (Sender) value;
        cell.setText(msg.getEmail());
      }
    });
    sender.build();

    ColumnBuilder recipients = t.createColumn("Recipients");
    recipients.bindToValue(MessageValues.RECIPIENT);
    recipients.setPercentWidth(COLUMN_WIDTH_RECIPIENTS);
    recipients.format(new ICellFormatter() {
      /**
       * Show recipients' eMails
       */
      @Override
      public void formatCell(ViewerCell cell, Object value) {
        cell.setText("");
        Collection<Recipient> list = (Collection<Recipient>) value;
        for (Recipient recipient : list) {
          if (cell.getText().length() == 0) {
            cell.setText(recipient.getEmail());
          } else {
            cell.setText(cell.getText() + ";" + recipient.getEmail());
          }
        }
      }
    });
    recipients.build();

    ColumnBuilder subject = t.createColumn("Subject");
    subject.bindToValue(MessageValues.SUBJECT);
    subject.setPercentWidth(COLUMN_WIDTH_SUBJECT);
    subject.build();

    tableViewer = t.getTableViewer();
    t.setInput(createDummyMessages());
    t.setInput(null); // must be this way -> empty table
    // Add as observer
    Scout.getInstance().addObserver(this);

    // Add selection provider and listener
    getSite().setSelectionProvider(tableViewer);

    // Add listener
    // getSite().getPage().addSelectionListener(new MailSelectedListener());
    // tableViewer.addSelectionChangedListener(new
    // MessageSelectionListener(parent.);
  }

  private Collection<Message> createDummyMessages() {
    return new RandomTestDataProvider(50).getMessages();
  }

  @Override
  public void setFocus() {
    tableViewer.getTable().setFocus();
  }

  @Override
  public void update(Observable arg0, Object obj) {
    // TODO Auto-generated method stub
    if (obj instanceof Collection<?>) {

      tableViewer.setInput(obj);
    }
  }
}
