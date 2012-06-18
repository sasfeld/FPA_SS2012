package de.bht.fpa.mail.s778455.imapnavigation.composite;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.swt.widgets.Display;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s778455.imapnavigation.jobs.ImapSynchJob;
import de.bht.fpa.mail.s778455.imapnavigation.observer.Scout;

/**
 * The root compositum which contains Account instances.
 * 
 * @author Sascha Feldmann
 * 
 */
public class IMAPDummyContainer extends IMAPItem {

  private static IMAPDummyContainer instance;
  private final Set<Account> accounts;

  private IMAPDummyContainer() {
    this.accounts = new HashSet<Account>();
  }

  /**
   * Singleton-Pattern, so that it can easily be used within the plugin
   * 
   * @return the instance
   */
  public static IMAPDummyContainer getInstance() {
    if (instance == null) {
      instance = new IMAPDummyContainer();
    }
    return instance;
  }

  /**
   * Add an account.
   * 
   * @param account
   *          - the Account to be added
   */
  public void addAccount(Account account) {
    accounts.add(account);
  }

  /**
   * Return a set of all "wrapped" accounts.
   * 
   * @return the set
   */
  public Set<Account> getAccounts() {
    return this.accounts;
  }

  @Override
  public boolean hasChildren() {
    return this.accounts.isEmpty();
  }

  @Override
  public IMAPItem[] getChildren() {
    IMAPItem[] items = new IMAPItem[this.accounts.size()];
    int i = 0;
    for (Account account : this.accounts) {
      items[i] = new IMAPAccount(account);
      ++i;
    }

    return items;
  }

  /**
   * Snychronize each account and notify the observers if ready.
   */
  public void synchronize() {
    for (Account account : this.accounts) {
      final Job job = new ImapSynchJob(account);
      job.setPriority(Job.LONG); // IMAP could take a longer time
      job.setUser(true);
      job.addJobChangeListener(new JobChangeAdapter() {
        @Override
        public void done(IJobChangeEvent event) {
          System.out.println("Snych-Job done.");
          Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
              Scout.getInstance().sendMessage();
            }

          });

        }
      });
      job.schedule();
    }

  }
}
