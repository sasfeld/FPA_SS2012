package de.bht.fpa.mail.s778455.filter;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

/**
 * @author slash
 * 
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

  @SuppressWarnings("javadoc")
  public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
    super(configurer);
  }

  @Override
  protected void makeActions(IWorkbenchWindow window) {
  }

  @Override
  protected void fillMenuBar(IMenuManager menuBar) {
  }

}
