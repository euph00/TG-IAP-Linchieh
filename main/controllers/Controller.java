package main.controllers;

import main.commands.Quit;
import main.commands.Record;
import main.commands.transactions.Deposit;
import main.commands.Noop;
import main.commands.transactions.Withdraw;
import main.models.Account;
import main.views.View;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Representation of the business logic that checks user input and dispatches <code>Commands</code>
 */
public class Controller {

    private final Scanner sc = new Scanner(System.in);

    private static final Map<String, String> VALID_COMMANDS =
            new HashMap<>() {{
                put("DEPOSIT", "D");
                put("WITHDRAW", "W");
                put("PRINT", "P");
                put("QUIT","Q");
            }};

    /**
     * Takes an input from the user and performs the appropriate actions to handle the request
     * @param input Input from the user as command line input
     * @param view The instance of <code>View</code> handling the user interface
     * @param account The <code>Account</code> that the user is operating on
     * @return A <code>Record</code> containing the <code>Command</code> that was executed
     */
    public Record executeInput(String input, View view, Account account) {
        input = sanitiseInput(input);

        if (!VALID_COMMANDS.containsValue(input)) {
            view.warnInvalidCommand();
            return new Noop().execute(account); // no operation carried out due to invalid command
        } else {
            return handleInput(input, view, account);
        }
    }

    /**
     * Handles the user input after <code>executeInput</code> verifies its validity
     * @return <code>Record</code> containing the <code>Command</code> that was executed
     * as a result of user input
     */
    private Record handleInput(String input, View view, Account account) {
        if (input.equals(VALID_COMMANDS.get("DEPOSIT"))) {
            return beginDepositProcess(view, account);
        } else if (input.equals(VALID_COMMANDS.get("WITHDRAW"))) {
            return beginWithdrawalProcess(view, account);
        } else if (input.equals(VALID_COMMANDS.get("PRINT"))) {
            return beginPrintStatementProcess(view, account);
        } else if (input.equals(VALID_COMMANDS.get("QUIT"))) {
            return beginQuitProgramProcess(view, account);
        }
        return new Noop().execute(account);
    }

    /**
     * Runs the workflow for depositing money to an account
     * @param view The UI instance that changes should be displayed on
     * @param account The <code>Account</code> that money is being deposited to
     * @return A <code>Record</code> containing either a <code>Transaction</code>
     * if the deposit was successful, or containing a no-op <code>Command</code> if the
     * deposit was not successful
     */
    private Record beginDepositProcess(View view, Account account) {
        view.queryAmountDesired("deposit");
        String input = sc.nextLine();

        if (!inputIsNumeric(input)) { // verify if input is numeric
            view.warnInvalidValue();
            return new Noop().execute(account);
        }

        try {
            BigDecimal value = new BigDecimal(input);
            if (monetaryValueIsValid(value)) {
                Deposit deposit = new Deposit(value);
                return deposit.execute(account);
            } else {
                view.warnInvalidValue();
            }
        } catch (InputMismatchException e) {
            view.warnInvalidValue();
        }
        return new Noop().execute(account);
    }

    /**
     * Runs the workflow for withdrawing money from an account
     * @param view The UI instance that changes should be displayed on
     * @param account The <code>Account</code> that money is being withdrawn from
     * @return A <code>Record</code> containing either a <code>Transaction</code> if the
     * withdrawal was successful, or containing a no-op <code>Command</code> if the withdrawal
     * was not successful
     */
    private Record beginWithdrawalProcess(View view, Account account) {
        view.queryAmountDesired("withdraw");
        String input = sc.nextLine();

        if (!inputIsNumeric(input)) { // verify if input is numeric
            view.warnInvalidValue();
            return new Noop().execute(account);
        }

        try {
            BigDecimal value = new BigDecimal(input);
            if (monetaryValueIsValid(value) && withdrawValueIsValid(value, account)) {
                Withdraw withdraw = new Withdraw(value);
                return withdraw.execute(account);
            } else {
                view.warnInvalidValue();
            }
        } catch (InputMismatchException e) {
            view.warnInvalidValue();
        }
        return new Noop().execute(account);
    }

    /**
     * Verifies if the input string is a numeric string
     */
    private boolean inputIsNumeric(String input) {
        try {
            BigDecimal bd = new BigDecimal(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Runs the workflow for displaying the account statement
     * @param view The UI instance that the statement should be displayed on
     * @param account The <code>Account</code> for which the statement should be displayed
     * @return A <code>Record</code> containing a no-op <code>Command</code>, as displaying
     * statements do not effect changes on the account
     */
    private Record beginPrintStatementProcess(View view, Account account) {
        view.printStatement(account);
        return new Noop().execute(account);
    }

    /**
     * Runs the workflow for quitting the program
     * @param view The UI instance that the exit message should be displayed on
     * @param account The <code>Account</code> that the user was accessing when this command
     *                was issued. While this parameter does not affect execution, it is
     *                recommended that this be set to a meaningful non-null value
     * @return A <code>Record</code> containing a no-op <code>Command</code>, as quitting the
     * program does not effect changes on the account. Also see <code>Quit::execute</code> for
     * the significance of this return value
     */
    private Record beginQuitProgramProcess(View view, Account account) {
        view.printGoodbyeMessage();
        return new Quit().execute(account);
    }

    /**
     * Cleans the user input. By converting all inputs to uppercase internally, the program
     * can accept both upper and lower case input from the user
     * @return <code>input</code> after sanitisation
     */
    private String sanitiseInput(String input) {
        return input.toUpperCase();
    }

    /**
     * Checks if <code>value</code> is a monetary value, which has 2 decimal places
     * and is non-negative
     */
    private boolean monetaryValueIsValid(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) > 0 && hasTwoDecimal(value);
    }

    /**
     * Verifies that a double has 2 decimal places at most
     */
    private boolean hasTwoDecimal(BigDecimal input) {
        BigInteger val = (input.multiply(BigDecimal.valueOf(100))).toBigInteger();
        return input.multiply(BigDecimal.valueOf(100)).compareTo(new BigDecimal(val.toString())) == 0;
    }

    /**
     * Verifies that the user is allowed to withdraw <code>amount</code> from <code>account</code>
     */
    private boolean withdrawValueIsValid(BigDecimal amount, Account account) {
        return amount.compareTo(account.getBalance()) < 0; //amount to withdraw less than balance
    }


}
