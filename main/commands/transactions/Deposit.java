package main.commands.transactions;

import main.commands.Record;
import main.models.Account;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representation of a deposit action
 */
public class Deposit extends Transaction {

    /**
     * Initialises a <code>Deposit</code> object representing the action of
     * depositing some <code>amount</code>
     * @param amount The amount to be deposited, in dollars
     */
    public Deposit(BigDecimal amount) {
        super.amount = amount;
        super.action = "DEPOSIT";
        super.datetime = new Date();
    }

    /**
     * Executes the action of depositing the value specified in this object
     * by incrementing the balance of <code>account</code>
     * @param account The target account to deposit to
     * @return A <code>Record</code> containing this <code>Transaction</code>.
     * A <code>Transaction</code> is a <code>Command</code>
     */
    @Override
    public Record execute(Account account) {
        account.setBalance(account.getBalance().add(amount));
        Record readOnlyRecord = new Record(this, account.getBalance());
        account.setHistory(readOnlyRecord);
        return readOnlyRecord;
    }
}
