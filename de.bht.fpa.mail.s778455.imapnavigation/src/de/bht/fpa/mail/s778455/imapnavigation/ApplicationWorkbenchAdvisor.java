package de.bht.fpa.mail.s778455.imapnavigation;

import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * @author slash
 * 
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

  private static final String PERSPECTIVE_ID = "de.bht.fpa.mail.s778455.imapnavigation.perspective"; //$NON-NLS-1$

  @Override
  public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
    return new ApplicationWorkbenchWindowAdvisor(configurer);
  }

  @Override
  public String getInitialWindowPerspectiveId() {
    return PERSPECTIVE_ID;
  }
}
