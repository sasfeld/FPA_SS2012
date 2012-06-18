package de.bht.fpa.mail.s778455.imapnavigation.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import de.bht.fpa.mail.s000000.common.mail.imapsync.ImapHelper;
import de.bht.fpa.mail.s000000.common.mail.imapsync.SynchronizationException;
import de.bht.fpa.mail.s000000.common.mail.model.Account;

/**
 * This class realizes a synchronize job for a given IMAP-Account.
 * 
 * @author Sascha Feldmann
 * 
 */
public class ImapSynchJob extends Job {

  private final Account account;

  /**
   * Create a new job.
   * 
   * @param account
   *          - the IMAP-Account
   */
  public ImapSynchJob(final Account account) {
    super(account.getName());
    this.account = account;
  }

  @Override
  protected IStatus run(final IProgressMonitor monitor) {
    try {
      ImapHelper.syncAllFoldersToAccount(account, monitor);
      return Status.OK_STATUS;
    } catch (SynchronizationException e) {
      return Status.CANCEL_STATUS;
    }

  }

}
