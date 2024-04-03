package tests;

import main.commands.Record;
import main.models.Account;
import main.views.View;

/**
 * Stub to simulate <code>View</code>. Only use for unit testing
 */
public class ViewStub extends View {
    ViewStub() {
        super();
    }

    @Override
    public void displayMenu(Record r) {}

    @Override
    public void printStatement(Account account) {}

    @Override
    public void warnInvalidCommand() {}

    @Override
    public void queryAmountDesired(String action) {}

    @Override
    public void warnInvalidValue() {}

    @Override
    public void printGoodbyeMessage() {}
}
