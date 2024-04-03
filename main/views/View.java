package main.views;

import main.commands.Record;
import main.models.Account;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton representing the UI. This class handles the output of the program to user
 */
public class View {

    private static final Map<String, String> headers =
            new HashMap<>() {{
                put("DEPOSIT", " has been deposited to your account.");
                put("WITHDRAW", " has been withdrawn.");
            }};

    private static final String DEFAULT_HEADER =
            "Welcome to AwesomeGIC Bank! What would you like to do?";

    private static final String HEADER_PREFIX = "\nThank you. $";

    private static final String MENU = "[D]eposit\n[W]ithdraw\n[P]rint statement\n[Q]uit";

    private static final String NEXT_ACTION = "Is there anything else you'd like to do?";

    private static final String INVALID_COMMAND =
            "\nThe command is invalid. Please enter either D, W, P or Q.";

    private static final String REQUEST_STRING =
            "Please enter the amount to ";

    private static final String INVALID_MONEY =
            "\nThe amount entered was invalid. Please enter a positive value with at most 2 decimal places." +
                    " Overdraft on this account is not allowed.";

    private static final String UNKNOWN_ERROR =
            "\nAn unknown error has occurred. Please try again.";

    private static final String GOODBYE_MESSAGE =
            "Thank you for banking with AwesomeGIC Bank.\n" +
                    "Have a nice day!";

    private static View INSTANCE;

    /**
     * Do not call constructor directly
     */
    protected View() {}

    /**
     * Returns the instance of the view for the current session
     * @return main.views.View object that handles the UI
     */
    public static View getView() {
        if (INSTANCE == null) {
            INSTANCE = new View();
        }
        return INSTANCE;
    }

    /**
     * Displays the menu to prompt the user for the next action
     * based on the previous action
     * @param r Record that contains the <code>Command</code>
     *          previously executed
     */
    public void displayMenu(Record r) {
        if (r == null) { // On startup
            printMenu(DEFAULT_HEADER);
        } else if (r.containsTransaction()) {
            printMenu(HEADER_PREFIX
                    + r.getAmount()
                    + headers.get(r.getAction())
                    + "\n"
                    + NEXT_ACTION);
        } else {
            printMenu(NEXT_ACTION);
        }
    }

    /**
     * Displays the statement of the account
     * @param account The account that the user wishes
     *                to display the statement for
     */
    public void printStatement(Account account) {
        List<String> dates = new ArrayList<>();
        List<String> amounts = new ArrayList<>();
        List<String> balances = new ArrayList<>();

        extractRecords(account, dates, amounts, balances);
        formatRecords(dates, amounts, balances);
        printRecords(dates, amounts, balances);
    }

    /**
     * Displays a warning to the user that the program
     * received an invalid input command
     */
    public void warnInvalidCommand() {
        System.out.println(INVALID_COMMAND);
    }

    /**
     * Displays a message asking the user to input the
     * monetary value for their transaction
     * @param action Determined by the nature of the
     *               transaction triggering this prompt
     */
    public void queryAmountDesired(String action) {
        System.out.println(REQUEST_STRING + action + ":");
    }

    /**
     * Displays a warning to the user that the program
     * received an invalid input for monetary value
     */
    public void warnInvalidValue() {
        System.out.println(INVALID_MONEY);
    }

    /**
     * Handles the printing of values for <code>displayMenu</code>
     * @param header The string to be printed before the menu
     */
    private void printMenu(String header) {
        System.out.println(header);
        System.out.println(MENU);
    }

    /**
     * Displays a goodbye message to the user before exiting
     */
    public void printGoodbyeMessage() {
        System.out.println(GOODBYE_MESSAGE);
    }

    /**
     * Extracts information from <code>Record</code>s from the
     * transaction history in the account for which to display
     * the statement. Helper for <code>PrintStatement</code>
     * @param account <code>Account</code> for which the statement
     *                is to be printed
     * @param dates Empty <code>List</code> to be populated
     * @param amounts Empty <code>List</code> to be populated
     * @param balances Empty <code>List</code> to be populated
     */
    private void extractRecords(Account account,
                                List<String> dates,
                                List<String> amounts,
                                List<String> balances) {

        dates.add("Date");
        amounts.add("Amount");
        balances.add("Balance");

        for (Record record : account.getHistory()) {
            if (!record.containsTransaction()) continue;

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ssa");
            dates.add(dateFormat.format(record.getDateTime()));

            balances.add(record.getBalance());

            if (record.getAction().equals("DEPOSIT")) {
                amounts.add(record.getAmount());
            } else if (record.getAction().equals("WITHDRAW")) {
                amounts.add("-" + record.getAmount());
            } else {
                amounts.add("NIL");
            }

        }

        if (dates.size() == 1) {
            dates.add("No transactions yet");
            amounts.add("NIL");
            balances.add("0.00");
        }
    }

    /**
     * Right pads the strings in each list appropriately such that the
     * table columns line up
     * @param dates <code>List</code> of dates of transactions in
     *              string format
     * @param amounts <code>List</code> of values of transactions
     *                in string format
     * @param balances <code>List</code> of balances after each
     *                 transaction in string format
     */
    private void formatRecords(List<String> dates,
                               List<String> amounts,
                               List<String> balances) {

        int longestDateLength = dates.stream().map(String::length).max(Integer::compare).orElse(0);
        int longestAmountLength = amounts.stream().map(String::length).max(Integer::compare).orElse(0);
        int longestBalanceLength = balances.stream().map(String::length).max(Integer::compare).orElse(0);

        for (int i = 0; i < dates.size(); i++) {
            dates.set(i, rightPadToLength(dates.get(i), longestDateLength));
            amounts.set(i, rightPadToLength(amounts.get(i), longestAmountLength));
            balances.set(i, rightPadToLength(balances.get(i), longestBalanceLength));
        }
    }

    /**
     * Handles the printing of the table representing the statement
     * @param dates <code>List</code> of dates of transactions in
     *              string format after right padding
     * @param amounts <code>List</code> of values of transactions
     *                in string format after right padding
     * @param balances <code>List</code> of balances after each
     *                 transaction in string format after right
     *                 padding
     */
    private void printRecords(List<String> dates,
                              List<String> amounts,
                              List<String> balances) {

        System.out.println();
        for (int i = 0; i < dates.size(); i++) {
            System.out.println(
                    dates.get(i) + " | " + amounts.get(i) + " | " + balances.get(i)
            );
        }
        System.out.println();
    }

    /**
     * Right pads a string with blank spaces
     * @param s <code>String</code> to be right padded
     * @param length Length of the string after right padding
     *               if the string is shorter than <code>length</code>
     * @return Padded string
     */
    private String rightPadToLength(String s, int length) {
        return String.format("%1$-" + length + "s", s);
    }
}
