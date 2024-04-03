package main;

import main.commands.Record;
import main.controllers.Controller;
import main.models.Account;
import main.views.View;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Controller controller = new Controller();
        Account account = new Account();
        View ui = View.getView();
        Record prevCommand = null;

        while (prevCommand == null || !prevCommand.getAction().equals("QUIT")) {
            ui.displayMenu(prevCommand);
            String input = sc.nextLine();
            prevCommand = controller.executeInput(input, ui, account);
        }
    }
}
