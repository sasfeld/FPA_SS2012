package de.bht.fpa.mail.s778455.filter.decorator;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.IFilter;

/**
 * This interface follows the Decorator - Pattern. You can insert a special
 * message detail, that will be used for the filter function.
 * 
 * The interface represents the COMPONENT.
 * 
 * @author Sascha Feldmann
 * @version 21.05.12
 * 
 */
public abstract class MessageFilter implements IFilter {
  protected final String filterText;
  protected final FilterOperator operator;

  /**
   * Create a new MessageFilter.
   * 
   * @param searchText
   *          - the String to be filtered.
   * @param filterText
   *          - the filter String.
   * @param mode
   *          - the filter Mode
   * 
   */
  public MessageFilter(String filterText, FilterOperator filterOp) {
    this.filterText = filterText;
    this.operator = filterOp;
  }

  public String getFilterText() {
    return this.filterText;
  }

  public FilterOperator getFilterOperator() {
    return this.operator;
  }
}
