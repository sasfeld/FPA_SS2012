package de.bht.fpa.mail.s778455.filter.parser;

import java.util.Iterator;

import de.bht.fpa.mail.s000000.common.filter.FilterCombination;
import de.bht.fpa.mail.s000000.common.filter.FilterDialog;
import de.bht.fpa.mail.s000000.common.filter.FilterGroupType;
import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.FilterType;

/**
 * This static class provides methods to build commands for the
 * FilterCommandParser. It's designed to be used with the results of the
 * FilterDialog.
 * 
 * @author Sascha Feldmann
 * 
 */
public class ParserCommandBuilder {

  public static String buildCommand(FilterDialog fd) {
    String command = "";
    String endOfCommand = "";
    String groupOp = "";
    FilterGroupType groupOperator = fd.getFilterGroupType();
    if (groupOperator.equals(FilterGroupType.UNION)) {
      command = "Union(";
      endOfCommand = ")";
      groupOp = "Union(";
    } else if (groupOperator.equals(FilterGroupType.INTERSECTION)) {
      command = "Intersection(";
      endOfCommand = ")";
      groupOp = "Intersection(";
    }
    Iterator<FilterCombination> it = fd.getFilterCombinations().iterator();
    // for (FilterCombination filter : fd.getFilterCombinations()) {
    while (it.hasNext()) {
      FilterCombination filter = it.next();
      FilterOperator filterOperator = filter.getFilterOperator();

      FilterType filterType = filter.getFilterType();
      String typeBeginn = filterType.toString().substring(0, 1);
      String type = typeBeginn + filterType.toString().substring(1, filterType.toString().length()).toLowerCase();
      Object filterValue = filter.getFilterValue();
      command += type + "(\"" + filterValue + "\", " + filterOperator.toString().toLowerCase() + ")";

      // Check for further filters, then do operation saved in command again
      // command += ", Sender(\"\", contains)";
      if (it.hasNext()) {
        command += ", " + groupOp;
        endOfCommand += ")";
      } else {
        command += ", " + type + "(\"" + filterValue + "\", " + filterOperator.toString().toLowerCase() + ")";
      }
    }

    command += endOfCommand;
    return command;
  }
}
