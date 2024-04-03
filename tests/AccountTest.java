package tests;

import main.commands.Record;
import main.controllers.Controller;
import main.models.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class AccountTest {
    @Test
    public void transactionHistory_onlyTransactions() {
        String values = "200\n150\n300.44\n200.12\n";

        InputStream old = System.in;
        InputStream depositValue = new ByteArrayInputStream(values.getBytes());

        System.setIn(depositValue);

        ViewStub testView = new ViewStub();
        Account testAccount = new Account();
        Controller controller = new Controller();

        controller.executeInput("d", testView, testAccount); // deposit 200
        controller.executeInput("w", testView, testAccount); // withdraw 150
        controller.executeInput("p", testView, testAccount); // print statement
        controller.executeInput("D", testView, testAccount); // deposit 300.44
        controller.executeInput("W", testView, testAccount); // withdraw 200.12
        controller.executeInput("p", testView, testAccount); // print statement

        assertEquals(4, testAccount.getHistory().size());
        for (Record record : testAccount.getHistory()) {
            assertTrue(record.containsTransaction());
        }
    }
}
