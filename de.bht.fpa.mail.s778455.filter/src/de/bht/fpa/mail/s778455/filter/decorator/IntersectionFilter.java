package de.bht.fpa.mail.s778455.filter.decorator;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * This class realizes the Union.
 * 
 * @author slash
 * 
 */
public class IntersectionFilter extends DecoratorFilter {

  // public Union(MessageFilter filter, String filterText, FilterOperator
  // filterOp) {
  // super(filter, filterText, filterOp);
  // }
  public IntersectionFilter(MessageFilter filter, MessageFilter compFilter) {
    super(filter, compFilter);
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    HashSet<Message> results = new HashSet<Message>();
    Set<Message> unionFirstResults = filter.filter(messagesToFilter);
    Set<Message> unionSecondResults = comparerFilter.filter(messagesToFilter);

    // Fix bug here - the reults have to be checked about their filterTexts
    for (Message secondMessage : unionSecondResults) {
      for (Message firstMessage : unionFirstResults) {
        if (firstMessage.toString().contains(secondMessage.toString())) {
          results.add(secondMessage);
        }
      }
    }

    return results;
  }
}
