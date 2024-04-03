package main.commands;

import main.models.Account;

/**
 * Representation of a non-operation, which occurs in the case that no writes
 * occur to an account as the result of an action
 */
public class Noop extends Command {

    /**
     * Initialises a <code>Noop</code> object representing a non-operation
     */
    public Noop() {
        super.action = "NOOP";
    }

    /**
     * Executes the non-operation on the <code>account</code>, where the
     * account data remains unchanged
     * @param account It is recommended
     *                to set this value to a meaningful value rather than null,
     *                although in practice there is no difference
     * @return A <code>Record</code> containing this <code>Command</code>
     */
    @Override
    public Record execute(Account account) {
        return new Record(this, account.getBalance());
    }

}
