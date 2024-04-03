package main.commands;

import main.models.Account;

/**
 * Representation of a <code>Quit</code> action
 */
public class Quit extends Command {

    /**
     * Initialises a <code>Quit</code> object representing an operation
     * to exit the program
     */
    public Quit() {
        super.action = "QUIT";
    }

    /**
     * Executes the shutdown of the program instance. JVM will exit
     * with code 0
     * @param account It is recommended
     *                to set this value to a meaningful value rather than null,
     *                although in practice there is no difference
     * @return A <code>Record</code> containing this <code>Command</code>.
     * Under normal circumstances, the return statement should not be reached.
     * However, in the case that JVM fails to exit for some reason, this return
     * value will be passed back to the main loop, which will serve as a backup
     * exit point for the program.
     */
    @Override
    public Record execute(Account account) {
        System.exit(0);
        return new Record(this, account.getBalance());
    }
}
