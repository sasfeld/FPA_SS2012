package de.bht.fpa.mail.s778455.filter.parser;

import java.util.ArrayList;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s778455.filter.decorator.ImportanceFilter;
import de.bht.fpa.mail.s778455.filter.decorator.IntersectionFilter;
import de.bht.fpa.mail.s778455.filter.decorator.MessageFilter;
import de.bht.fpa.mail.s778455.filter.decorator.ReadFilter;
import de.bht.fpa.mail.s778455.filter.decorator.RecipientFilter;
import de.bht.fpa.mail.s778455.filter.decorator.SenderFilter;
import de.bht.fpa.mail.s778455.filter.decorator.SubjectFilter;
import de.bht.fpa.mail.s778455.filter.decorator.TextFilter;
import de.bht.fpa.mail.s778455.filter.decorator.UnionFilter;

/**
 * This class modelles the parser for input commands to realise the filter
 * functions.
 * 
 * @author Sascha Feldmann
 * 
 */
public class FilterCommandParser {
  private static final String IMPORTANCE = "Importance";
  private static final String READ = "Read";
  private static final String RECIPIENT = "Recipient";
  private static final String SENDER = "Sender";
  private static final String SUBJECT = "Subject";
  private static final String TEXT = "Text";
  private static final String UNION = "Union";
  private static final String INTERSECTION = "Intersection";

  private static final FilterCommandParser instance = new FilterCommandParser();
  private static final Object CONTAINS = "contains";
  private static final Object CONTAINS_NOT = "containsNot";
  private static final Object STARTS_WITH = "startsWith";
  private static final Object ENDS_WITH = "endsWith";
  private static final Object IS = "is";
  /**
   * Recursive commands.
   */
  private static ArrayList<String> recCommands;
  /**
   * Final commands which contains no more filter functions.
   */
  private static ArrayList<String> finCommands;

  private FilterCommandParser() {

    adddFinCommands();
    adddRecCommands();
  }

  private void adddRecCommands() {
    // TODO Auto-generated method stub
    recCommands = new ArrayList<String>();
    recCommands.add(UNION);
    recCommands.add(INTERSECTION);
  }

  private void adddFinCommands() {
    // TODO Auto-generated method stub
    finCommands = new ArrayList<String>();
    finCommands.add(IMPORTANCE);
    finCommands.add(READ);
    finCommands.add(RECIPIENT);
    finCommands.add(SENDER);
    finCommands.add(SUBJECT);
    finCommands.add(TEXT);
  }

  public static FilterCommandParser getInstance() {
    return instance;
  }

  /*
   * Commands: Sender("me@this.com", contains) Read(false)
   * Union(Sender("me@this.com", contains), Recipient("foo@bar.de", is))
   */
  public MessageFilter parseCommand(String command) {
    // Global string formats
    command = command.trim();
    command = command.replace(", ", ","); // Replace whitespaces - not allowed

    // First chain
    int endPosFirstCom = command.indexOf("(");
    String firstCom = command.substring(0, endPosFirstCom);

    if (finCommands.contains(firstCom)) {
      // Split arguments
      String head = command.substring(endPosFirstCom + 1, command.length() - 1);
      String[] arguments = head.split(",");
      if (arguments.length > 2) {
        throw new IllegalArgumentException("The argument weight is invalid. Use 2 arguments only.");
      }
      // Decorate here
      MessageFilter msgFilter = translateFinalCommand(firstCom, arguments);
      return msgFilter;
    } else if (recCommands.contains(firstCom)) {
      // Do recursion here
      String head = command.substring(endPosFirstCom + 1, command.length() - 1);
      String[] arguments = head.split("[(].*[)],"); // split the two arguments

      if (arguments.length > 2) {
        throw new IllegalArgumentException("The argument weight is invalid. Use 2 arguments only.");

      }
      int endPosFirstArg = head.indexOf(arguments[1]) - 1;
      String firstArg = head.substring(0, endPosFirstArg);
      String secondArg = arguments[1];

      MessageFilter firstFilter = parseCommand(firstArg);
      MessageFilter secondFilter = parseCommand(secondArg);
      MessageFilter msgFilter = translateRecursiveCommand(firstCom, firstFilter, secondFilter);
      return msgFilter;
    } else {
      throw new IllegalArgumentException("Unknown command for command in parseCommand - value: " + firstCom);
    }
  }

  /**
   * This methode creates the filter decorator objects for the given command and
   * arguments.
   * 
   * @param firstCom
   *          - the filter command
   * @param firstFilter
   *          - the first filter to be decorated.
   * @param secondFilter
   *          - the second filter to be decorated
   */
  private MessageFilter translateRecursiveCommand(String firstCom, MessageFilter firstFilter, MessageFilter secondFilter) {
    MessageFilter filter = null;
    if (firstCom.equals(UNION)) {
      filter = new UnionFilter(firstFilter, secondFilter);
      return filter;
    } else if (firstCom.equals(INTERSECTION)) {
      filter = new IntersectionFilter(firstFilter, secondFilter);
      return filter;
    } else {
      throw new IllegalArgumentException("Undefined concatenation operator.");
    }
  }

  /**
   * This methode creates the filter objects for the given command and
   * arguments.
   * 
   * @param firstCom
   *          - the filter command
   * @param arguments
   *          - an array of arguments which must contain at least one argument
   *          but not more than two.
   */
  private MessageFilter translateFinalCommand(String firstCom, String[] arguments) {
    MessageFilter filter = null;
    String filterText = arguments[0].replace("\"", "");

    // Fetch FilterOperator
    FilterOperator filterOperator = FilterOperator.IS; // Standard for READ
    if (arguments.length > 1) {
      if (arguments[1].equals(CONTAINS)) {
        filterOperator = FilterOperator.CONTAINS;
      }
      if (arguments[1].equals(CONTAINS_NOT)) {
        filterOperator = FilterOperator.CONTAINS_NOT;
      }
      if (arguments[1].equals(STARTS_WITH)) {
        filterOperator = FilterOperator.STARTS_WITH;
      }
      if (arguments[1].equals(ENDS_WITH)) {
        filterOperator = FilterOperator.ENDS_WITH;
      }
      if (arguments[1].equals(IS)) {
        filterOperator = FilterOperator.IS;
      }
    }

    if (firstCom.equals(SENDER)) {
      filter = new SenderFilter(filterText, filterOperator);
      return filter;
    } else if (firstCom.equals(RECIPIENT)) {
      filter = new RecipientFilter(filterText, filterOperator);
      return filter;
    } else if (firstCom.equals(SUBJECT)) {
      filter = new SubjectFilter(filterText, filterOperator);
      return filter;
    } else if (firstCom.equals(TEXT)) {
      filter = new TextFilter(filterText, filterOperator);
      return filter;
    } else if (firstCom.equals(IMPORTANCE)) {
      filter = new ImportanceFilter(filterText, filterOperator);
      return filter;
    } else if (firstCom.equals(READ)) {
      filter = new ReadFilter(filterText, filterOperator);
      return filter;
    } else {
      throw new IllegalArgumentException("Undefined filter operator.");
    }

  }

  public static void main(String args[]) {
    FilterCommandParser com = FilterCommandParser.getInstance();
    com.parseCommand("Union(Sender(\"me@this.com\", contains), Recipient(\"foo@bar.de\", is))");
  }
}
