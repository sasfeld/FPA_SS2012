package de.bht.fpa.mail.s778455.filter;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s778455.filter.decorator.IntersectionFilter;
import de.bht.fpa.mail.s778455.filter.decorator.MessageFilter;
import de.bht.fpa.mail.s778455.filter.decorator.RecipientFilter;
import de.bht.fpa.mail.s778455.filter.decorator.SenderFilter;
import de.bht.fpa.mail.s778455.filter.parser.FilterCommandParser;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

  // The plug-in ID
  public static final String PLUGIN_ID = "de.bht.fpa.mail.s778455.filter"; //$NON-NLS-1$

  // The shared instance
  private static Activator plugin;

  /**
   * The constructor
   */
  public Activator() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
   * )
   */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    plugin = this;

    // HIER TESTENR
    RandomTestDataProvider testData = new RandomTestDataProvider(50);
    List<Message> messages = testData.getMessages();

    // Test-Filter-String
    String filter = "test";
    SenderFilter senderFilter = new SenderFilter(filter, FilterOperator.CONTAINS);
    senderFilter.filter(messages);

    // Register execution listener
    final IWorkbench workbench = PlatformUI.getWorkbench();

    // ICommandService commandService = (ICommandService)
    // workbench.getService(ICommandService.class);
    // commandService.addExecutionListener(new filterExecutionListener());
    // Add observer to the fsnavigation - scout

  }

  public static void main(String[] args) {
    // testObjects();
    testParser();
  }

  /**
   * Test of the parser.
   */
  private static void testParser() {
    RandomTestDataProvider testData = new RandomTestDataProvider(5);
    List<Message> messages = testData.getMessages();
    for (Message msg : messages) {
      System.out.println(msg);
    }
    System.out.println("---------");
    String testCommand = "Intersection(Sender(\"arnold\", contains), Recipient(\"karl\", contains))";
    System.out.println("TestCommand: " + testCommand);
    MessageFilter result = FilterCommandParser.getInstance().parseCommand(testCommand);
    for (Message filterMessages : result.filter(messages)) {
      System.out.println(filterMessages);
    }

    System.out.println("---------");
    testCommand = "Read(false)";
    System.out.println("TestCommand: " + testCommand);
    result = FilterCommandParser.getInstance().parseCommand(testCommand);
    for (Message filterMessages : result.filter(messages)) {
      System.out.println(filterMessages);
    }

    System.out.println("---------");
    testCommand = "Union(Sender(\"me@this.com\", contains), Recipient(\"foo@bar.de\", is))";
    System.out.println("TestCommand: " + testCommand);
    result = FilterCommandParser.getInstance().parseCommand(testCommand);
    for (Message filterMessages : result.filter(messages)) {
      System.out.println(filterMessages);
    }

    System.out.println("---------");
    testCommand = "Intersection(Sender(\"mueller\", startsWith), Read(true))";
    System.out.println("TestCommand: " + testCommand);
    result = FilterCommandParser.getInstance().parseCommand(testCommand);
    for (Message filterMessages : result.filter(messages)) {
      System.out.println(filterMessages);
    }
  }

  /**
   * Test filter objects directly (non-parser)
   */
  private static void testObjects() {
    // HIER TESTENR
    RandomTestDataProvider testData = new RandomTestDataProvider(5);
    List<Message> messages = testData.getMessages();
    for (Message msg : messages) {
      System.out.println(msg);
    }
    // Test-Filter-String
    System.out.println("----");
    String filter = "stulle";
    SenderFilter senderFilter = new SenderFilter(filter, FilterOperator.CONTAINS);
    for (Message filterMessages : senderFilter.filter(messages)) {
      System.out.println(filterMessages);
    }

    // Union
    // String filter2 = "trude";
    // RecipientFilter recipientFilter = new RecipientFilter(filter2,
    // FilterOperator.CONTAINS);
    // Union unionFilter = new Union(senderFilter, new Union(new Sender(filter,
    // FilterOperator.IS), recipientFilter));
    // for (Message filterMessages : unionFilter.filter(messages)) {
    // System.out.println(filterMessages);
    // }
    System.out.println("----");
    String filter2 = "trude";
    RecipientFilter recipientFilter = new RecipientFilter(filter2, FilterOperator.CONTAINS);
    IntersectionFilter unionFilter = new IntersectionFilter(senderFilter, recipientFilter);
    for (Message filterMessages : unionFilter.filter(messages)) {
      System.out.println(filterMessages);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
   * )
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance
   * 
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
  }

  /**
   * Returns an image descriptor for the image file at the given plug-in
   * relative path
   * 
   * @param path
   *          the path
   * @return the image descriptor
   */
  public static ImageDescriptor getImageDescriptor(String path) {
    return imageDescriptorFromPlugin(PLUGIN_ID, path);
  }
}
