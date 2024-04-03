package tests;

import main.controllers.Controller;
import main.models.Account;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

public class ControllerTest {

    @Test
    public void executeInput_deposit_withdraw_usualCase() {
        String values = "200\n150\n300.44\n200.12\n";

        InputStream old = System.in;
        InputStream depositValue = new ByteArrayInputStream(values.getBytes());

        System.setIn(depositValue);

        ViewStub testView = new ViewStub();
        Account testAccount = new Account();
        Controller controller = new Controller();

        controller.executeInput("d", testView, testAccount); // deposit 200
        assertEquals(0, BigDecimal.valueOf(200).compareTo(testAccount.getBalance()));

        controller.executeInput("w", testView, testAccount); // withdraw 150
        assertEquals(0, BigDecimal.valueOf(50).compareTo(testAccount.getBalance()));

        controller.executeInput("D", testView, testAccount); // deposit 300.44
        assertEquals(0, BigDecimal.valueOf(350.44).compareTo(testAccount.getBalance()));

        controller.executeInput("W", testView, testAccount); // withdraw 200.12
        assertEquals(0, BigDecimal.valueOf(150.32).compareTo(testAccount.getBalance()));
    }

    @Test
    public void executeInput_deposit_withdraw_largeValue() {
        String values = "200000000000000000\n150000000000000000\n300000000000000000.44\n200000000000000000.12\n";

        InputStream old = System.in;
        InputStream depositValue = new ByteArrayInputStream(values.getBytes());

        System.setIn(depositValue);

        ViewStub testView = new ViewStub();
        Account testAccount = new Account();
        Controller controller = new Controller();

        controller.executeInput("d", testView, testAccount); // deposit 200000000000000
        assertEquals(new BigDecimal("200000000000000000").compareTo(testAccount.getBalance()), 0);


        controller.executeInput("w", testView, testAccount); // withdraw 150000000000000
        assertEquals(new BigDecimal("50000000000000000").compareTo(testAccount.getBalance()), 0);

        controller.executeInput("D", testView, testAccount); // deposit 300000000000000.44
        assertEquals(new BigDecimal("350000000000000000.44").compareTo(testAccount.getBalance()), 0);

        controller.executeInput("W", testView, testAccount); // withdraw 200000000000000.12
        assertEquals(new BigDecimal("150000000000000000.32").compareTo(testAccount.getBalance()), 0);
    }

    @Test
    public void executeInput_deposit_withdraw_overdraft() {
        String values = "200\n350\n0.44\n300.12\n";

        InputStream old = System.in;
        InputStream depositValue = new ByteArrayInputStream(values.getBytes());

        System.setIn(depositValue);

        ViewStub testView = new ViewStub();
        Account testAccount = new Account();
        Controller controller = new Controller();

        controller.executeInput("d", testView, testAccount); // deposit 200
        assertEquals(BigDecimal.valueOf(200).compareTo(testAccount.getBalance()), 0);

        controller.executeInput("w", testView, testAccount); // withdraw 350 should fail
        assertEquals(BigDecimal.valueOf(200).compareTo(testAccount.getBalance()), 0);

        controller.executeInput("D", testView, testAccount); // deposit 0.44
        assertEquals(BigDecimal.valueOf(200.44).compareTo(testAccount.getBalance()), 0);

        controller.executeInput("W", testView, testAccount); // withdraw 300.12 should fail
        assertEquals(BigDecimal.valueOf(200.44).compareTo(testAccount.getBalance()), 0);
    }

    @Test
    public void executeInput_deposit_withdraw_negativeValues() {
        String values = "200\n-150\n-0.44\n100.12\n";

        InputStream old = System.in;
        InputStream depositValue = new ByteArrayInputStream(values.getBytes());

        System.setIn(depositValue);

        ViewStub testView = new ViewStub();
        Account testAccount = new Account();
        Controller controller = new Controller();

        controller.executeInput("d", testView, testAccount); // deposit 200
        assertEquals(BigDecimal.valueOf(200).compareTo(testAccount.getBalance()), 0);

        controller.executeInput("w", testView, testAccount); // withdraw -150 should fail
        assertEquals(BigDecimal.valueOf(200).compareTo(testAccount.getBalance()), 0);

        controller.executeInput("D", testView, testAccount); // deposit -0.44 should fail
        assertEquals(BigDecimal.valueOf(200).compareTo(testAccount.getBalance()), 0);

        controller.executeInput("W", testView, testAccount); // withdraw 100.12
        assertEquals(BigDecimal.valueOf(99.88).compareTo(testAccount.getBalance()), 0);
    }
}
