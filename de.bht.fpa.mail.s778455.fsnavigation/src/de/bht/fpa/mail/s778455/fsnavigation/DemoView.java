package de.bht.fpa.mail.s778455.fsnavigation;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * @author slash
 * 
 */
public final class DemoView extends ViewPart {

  private TreeViewer viewer;

  @Override
  public void createPartControl(Composite parent) {
    viewer = new TreeViewer(parent);
    viewer.setLabelProvider(new LabelProvider());
    viewer.setContentProvider(new DemoContentProvider());
    viewer.setInput(createModel());
  }

  private String createModel() {
    return "Wurzel";
  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }
}
