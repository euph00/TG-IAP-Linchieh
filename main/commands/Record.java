package main.commands;

import main.commands.transactions.Transaction;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Read-only representation of an already executed command. This serves as
 * a wrapper to prevent a previous command from being re-executed unexpectedly
 */
public class Record {
    private final Command command;
    private final BigDecimal balance;

    /**
     * Instantiates a <code>Record</code> that holds a <code>Command</code>.
     * If the <code>Command</code> happens to be a <code>Transaction</code>,
     * the <code>balance</code> is the value of the account balance at the
     * end of the execution of <code>Command</code>. Otherwise, <code>balance</code>
     * should be set to -1
     * @param command The <code>Command</code> that has been executed, and
     *                should be stored for record keeping
     * @param balance The value of account balance after execution of <code>Command</code>,
     *                if it happens to be a <code>Transaction</code>
     */
    public Record(Command command, BigDecimal balance) {
        this.command = command;
        this.balance = balance;
    }

    /**
     * Gets the action tag of the <code>Command</code> stored
     * @return The string that is the action tag of the stored <code>Command</code>,
     * for example the string <code>DEPOSIT</code> is returned if the <code>Command</code>
     * is of type <code>Deposit</code>
     */
    public String getAction() {
        return command.action;
    }

    /**
     * Evaluates to <code>true</code> if the <code>Command</code> contained is of type
     * <code>Transaction</code>. This method should evaluate to <code>true</code> before
     * calling <code>Record::getAmount</code>, <code>Record::getDateTime</code> or
     * <code>Record::getBalance</code>
     * @return
     */
    public boolean containsTransaction() {
        return command instanceof Transaction;
    }

    /**
     * Gets the monetary value of the <code>Transaction</code> encapsulated in this
     * <code>Record</code>. This should only be called after verifying
     * <code>containsTransaction</code> is <code>true</code>
     * @return The monetary value of the <code>Transaction</code>, in dollars
     */
    public String getAmount() { //only call after verifying containstransaction
        if (!this.containsTransaction()) return null;
        Transaction transaction = (Transaction) command;
        DecimalFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(transaction.amount);
    }

    /**
     * Gets the date and time of the <code>Transaction</code> encapsulated in this
     * <code>Record</code>. This should only be called after verifying
     * <code>containsTransaction</code> is <code>true</code>
     * @return The date and time of the <code>Transaction</code>, as a
     * <code>Date</code> object
     */
    public Date getDateTime() {
        if (!this.containsTransaction()) return null;
        Transaction transaction = (Transaction) command;
        return transaction.datetime;
    }

    /**
     * Gets the balance of the account involved in the <code>Transaction</code>
     * encapsulated in this <code>Record</code> after the action completes. This
     * should only be called after verifying <code>containsTransaction</code> is
     * <code>true</code>
     * @return The balance of the account that performed the <code>transaction</code>
     * after the action completes
     */
    public String getBalance() {
        if (!this.containsTransaction()) return null;
//        if (balance.compareTo()) return "0.00";
        DecimalFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(balance);
    }
}
