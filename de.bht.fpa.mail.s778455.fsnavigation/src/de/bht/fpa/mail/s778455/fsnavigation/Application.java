package de.bht.fpa.mail.s778455.fsnavigation;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.
   * IApplicationContext)
   */
  @Override
  public Object start(IApplicationContext context) throws Exception {
    System.out.println("Hello RCP World!");
    return IApplication.EXIT_OK;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.equinox.app.IApplication#stop()
   */
  @Override
  public void stop() {
    // nothing to do
  }
}
