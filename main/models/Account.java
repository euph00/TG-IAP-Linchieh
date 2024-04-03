package main.models;

import main.commands.Record;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a bank account
 */
public class Account {
    private static final int INITIAL_BALANCE = 0;

    private BigDecimal balance;
    private final List<Record> history;

    /**
     * Creates a new account and initialises account balance to 0
     * with an empty transaction history
     */
    public Account() {
        balance = BigDecimal.valueOf(INITIAL_BALANCE);
        history = new ArrayList<>();
    }

    /**
     * Gets the value of the balance of the account
     * @return
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance of the account. This method is only to be used in
     * the <code>transactions</code> package
     */
    public void setBalance(BigDecimal value) {
        balance = value;
    }

    /**
     * Gets a copy of the account's transaction history
     */
    public List<Record> getHistory() {
        return new ArrayList<>(history);
    }

    /**
     * Adds a <code>Record</code> to the account's transaction history
     * @param record
     */
    public void setHistory(Record record) {
        history.add(record);
    }
}
