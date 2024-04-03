# Developer notes for Bank Account application
### This document contains notes on the limitations and design choices made for the application enclosed.

---
## Architecture
This application uses Model-View-Controller (MVC) as its underlying design pattern.
The Command design pattern is also present as an abstraction of the user's actions.
The application is launched through the `main.Main` class, which contains the primary loop.

The `controllers` package contains the `Controller` class, which handles most of the business
logic for this application. This class is dependent on both the `View` class and `Account` class,
as it needs to update the model (represented by `Account`) and the UI (represented by `View`).

The `models` package contains the `Account` class, which is an abstraction of a bank account.
This class is dependent on neither `Controller` nor `View`, as it is simply a data representation.

The `views` package contains the `View` class, which handles the user interface. Since this is a 
CLI based application, this class mostly handle the formatting of strings and data and printing to
the console. This class depends on `Account`, as it needs to pull data from the model.

The `commands` package contains the `Command` abstract base class, which is an abstraction of a user
action on the application. The sub-package `transactions` contains an abstract child of `Command` called
`Transaction`, which is an abstraction of actions that involve monetary changes to the account.

---
## Program flow
### A usual run of the program is as follows:
1. User runs the program, entry point from `main.Main`
2. `main.Main` calls `displayMenu` of `view`
3. User inputs an action
4. `main.Main` reads the input and calls `executeInput` of `Controller` on it
5. The execution completes and a `Record` containing the `Command` that was executed is returned
6. Return to step 3

### In particular, an example of what happens in step 4
#### Deposit
1. `Controller.executeInput` calls `Controller.handleInput` which identifies the input as a deposit request
2. `Controller.handleInput` calls `Controller.beginDepositProcess` which then calls `View.queryAmountDesired` to prompt 
user to input the desired amount to deposit
3. `Controller.beginDepositProcess` reads the user input value, checks its validity, and instantiates a `Deposit`
command which it executes, incrementing account balance. This `Deposit` transaction is wrapped in a `Record` and added
to the history of the `Account` that was operated on
4. The `Record` is also returned to `Controller.executeInput`, which in turn returns it to `main.Main`


---
## Known issues
* With extremely large numbers, the UI for statement printing is misaligned
---
## Future enhancements
* Support multiple accounts on the program
* Support transfer of money between accounts
* Support persistent storage after closing the application