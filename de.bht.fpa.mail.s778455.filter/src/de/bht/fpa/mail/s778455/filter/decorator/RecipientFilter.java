package de.bht.fpa.mail.s778455.filter.decorator;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * This class represents a concrete MessageFilter (Component) within the
 * Decorator Pattern.
 * 
 * @author Sascha Feldmann
 * 
 */
public class RecipientFilter extends MessageFilter {

  /**
   * Filter the recipients.
   * 
   * @param filterText
   *          - the text to filter
   * @param filterOp
   *          - the filter operation (contains, equals,...)
   */
  public RecipientFilter(String filterText, FilterOperator filterOp) {
    super(filterText, filterOp);
  }

  @Override
  /**
   * Filter for Sender.
   */
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    HashSet<Message> results = new HashSet<Message>();
    for (Message msg : messagesToFilter) {
      for (de.bht.fpa.mail.s000000.common.mail.model.Recipient recipient : msg.getRecipients()) {
        if (StringCompareHelper.matches(recipient.getEmail(), this.filterText, this.operator)
            || StringCompareHelper.matches(recipient.getPersonal(), this.filterText, this.operator)) {
          results.add(msg);
        }
      }
    }
    return results;
  }
}
