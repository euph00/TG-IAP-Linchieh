package main.commands.transactions;

import main.commands.Record;
import main.models.Account;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representation of a withdrawal action
 */
public class Withdraw extends Transaction {

    /**
     * Initialises a <code>Withdraw</code> object representing the action of
     * withdrawing some <code>amount</code>
     * @param amount The amount to be withdrawn, in dollars
     */
    public Withdraw(BigDecimal amount) {
        super.amount = amount;
        super.action = "WITHDRAW";
        super.datetime = new Date();
    }

    /**
     * Executes the action of depositing the value specified in this object
     * by decrementing the balance of <code>account</code>
     * @param account The target account to withdraw from
     * @return A <code>Record</code> containing this <code>Transaction</code>.
     * A <code>Transaction</code> is a <code>Command</code>
     */
    @Override
    public Record execute(Account account) {
        account.setBalance(account.getBalance().subtract(amount));
        Record readOnlyRecord = new Record(this, account.getBalance());
        account.setHistory(readOnlyRecord);
        return readOnlyRecord;
    }
}
