package de.bht.fpa.mail.s778455.fsnavigation.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import de.bht.fpa.mail.s778455.fsnavigation.observer.Scout;

public class HistoryDialog extends Dialog {
  private static final int HEIGHT = 100;
  private static final int WIDTH = 100;
  private ListViewer viewer;

  public HistoryDialog(Shell parentShell) {
    super(parentShell);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    viewer = new ListViewer(parent);

    viewer.setContentProvider(ArrayContentProvider.getInstance());

    showHistory();

    viewer.getControl().setSize(WIDTH, HEIGHT);

    return super.createDialogArea(parent);
  }

  /**
   * Show the history by adding it to the list viewer.
   */
  public void showHistory() {
    ArrayList<String> ls = loadHistory();
    if (ls != null) {
      viewer.getList().setEnabled(true);
      for (String el : ls) {
        viewer.add(el);
      }
    } else {
      viewer.add(NavigationView.MSG_HISTORY_EMPTY);
      viewer.getList().setEnabled(false);
    }
  }

  /**
   * Load all history directories from SER.
   * 
   * @return an array of file objects containing the directories.
   */
  public static ArrayList<String> loadHistory() {
    FileReader fr;
    ArrayList<String> ret;
    try {
      ret = new ArrayList<String>();
      fr = new FileReader(new File(NavigationView.PATH_HISTORYFILE));
      BufferedReader r = new BufferedReader(fr);

      String dir = r.readLine();
      while (dir != null && dir.length() > 1) {
        ret.add(dir);
        dir = r.readLine();
      }

      r.close();
      fr.close();

      return ret;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();
      return null;
    }

  }

  /**
   * // * Save the current File to the history. // * // * @param directory // *
   * - the directory to save //
   */
  public static void saveHistory(File directory) {
    try {
      ArrayList<String> old = loadHistory();

      FileWriter fw = new FileWriter(new File(NavigationView.PATH_HISTORYFILE));
      BufferedWriter writer = new BufferedWriter(fw);
      if (old != null) {
        for (String entry : old) {
          String repl = entry.replaceAll("\n", "");
          if (!repl.equals(directory.toString())) { // Check if file is already
                                                    // save
            writer.write(entry + "\n");
          }
        }
      }
      writer.write(directory.toString() + "\n");
      writer.flush();
      writer.close();

      fw.close();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Override
  /**
   * OK-Button-Handler
   */
  protected void okPressed() {
    if (!viewer.getSelection().isEmpty()) {
      String b = viewer.getSelection().toString();
      // Extract file path
      String test = b.substring(1, b.length() - 1);
      File curFile = new File(test);

      if (!b.equals(NavigationView.MSG_HISTORY_EMPTY)) {
        Scout.getInstance().newMessage(curFile);
      }
    }
    this.close();
  }
}
