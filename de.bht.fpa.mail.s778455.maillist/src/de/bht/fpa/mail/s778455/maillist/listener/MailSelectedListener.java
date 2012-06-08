package de.bht.fpa.mail.s778455.maillist.listener;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

/**
 * VON FRÜHER!
 * 
 * @author Sascha Feldmann
 * 
 */
public class MailSelectedListener implements ISelectionListener {

  @Override
  public void selectionChanged(IWorkbenchPart part, ISelection selection) {
    // TODO Auto-generated method stub
    System.out.println(part.getSite().getWorkbenchWindow().getPages());
  }

}
