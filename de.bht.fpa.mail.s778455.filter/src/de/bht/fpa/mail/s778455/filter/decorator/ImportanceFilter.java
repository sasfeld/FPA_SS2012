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
public class ImportanceFilter extends MessageFilter {

  /**
   * Filter for the importance.
   * 
   * @param filterText
   *          - the text to filter
   * @param filterOp
   *          - the filter operation (contains, equals,...)
   */
  public ImportanceFilter(String filterText, FilterOperator filterOp) {
    super(filterText, filterOp);
  }

  @Override
  /**
   * Filter for Sender.
   */
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    HashSet<Message> results = new HashSet<Message>();
    for (Message msg : messagesToFilter) {
      if (StringCompareHelper.matches(msg.getImportance().toString(), this.filterText, this.operator)) {
        results.add(msg);
      }
    }
    return results;
  }
}
