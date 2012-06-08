package de.bht.fpa.mail.s778455.filter.decorator;

/**
 * This class follows the Decorator - Pattern. You can insert a special message
 * detail, that will be used for the filter function.
 * 
 * This class represent the DECORATOR which knows another
 * MessageFilter/COMPONENT.
 * 
 * Decorators are: - the UNION filter - the INTERSECTION filter
 * 
 * @author Sascha Feldmann
 * @version 21.05.12
 * 
 */
public abstract class DecoratorFilter extends MessageFilter {
  /** The reference to the contained Filter */
  protected final MessageFilter filter;
  protected final MessageFilter comparerFilter;

  // public Decorator(MessageFilter filter, String filterText, FilterOperator
  // filterOp) {
  // super(filterText, filterOp);
  // this.filter = filter;
  // }
  /**
   * Create a new UNION or INTERSECTION filter.
   * 
   * @param filter
   *          on the left side
   * @param compareFilter
   *          filter on the right side
   */
  public DecoratorFilter(MessageFilter filter, MessageFilter compareFilter) {
    super("", null);

    this.filter = filter;
    this.comparerFilter = compareFilter;
  }

  /**
   * Return the current containing filter.
   * 
   * @return a MessageFilter.
   */
  public MessageFilter getMessageFilter() {
    return this.filter;
  }

}
