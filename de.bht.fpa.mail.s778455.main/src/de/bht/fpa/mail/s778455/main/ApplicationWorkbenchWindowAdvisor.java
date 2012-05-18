package de.bht.fpa.mail.s778455.main;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
    super(configurer);
  }

  @Override
  public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
    return new ApplicationActionBarAdvisor(configurer);
  }

  @Override
  public void preWindowOpen() {
    IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
    configurer.setInitialSize(new Point(WIDTH, HEIGHT));
    configurer.setShowCoolBar(false);
    configurer.setShowStatusLine(true);

    // Alternative
    configurer.setTitle("FPA Mailer");
  }

  /**
   * AddOn: Actions after the window was closed
   */
  @Override
  public void postWindowClose() {

  }

}
