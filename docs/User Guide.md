# Bank Account
Bank account is a simple banking system that handles operations on a bank account through the command
line.

---
## Quick start
1. Ensure you have at least Java 15 or any newer version installed on your computer. You can download it 
from [here](https://www.oracle.com/sg/java/technologies/downloads/archive/)
2. In file explorer open the folder containing the file `bankaccount.jar` and `bankaccount.bat`. They should be in the
same folder. If not, move them to the same folder.
3. Double-click the bankaccount.bat file to open the app

Alternatively, if you are familiar with the command line, you can open a terminal at the folder containing 
`bankaccount.jar` and use the command `java -jar bankaccount.jar` to run the application. `bankaccount.bat` is
not needed in this case.

---
## Features
On startup, the application will prompt you with
```aidl
Welcome to AwesomeGIC Bank! What would you like to do?
[D]eposit
[W]ithdraw
[P]rint statement
[Q]uit
```
Key in either `D` or `d` to make a deposit, `W` or `w` to make a withdrawal, `P` or `p` to view your account statement
and `Q` or `q` to close the application. Press the `Enter` key after you have keyed in your selection.

### Deposit - input `d`
If you choose to make a deposit, you will be prompted to input an amount to deposit, as such
```aidl
Please enter the amount to deposit:
```
At this point, please enter a sum to deposit. Acceptable values to deposit have at most 2 decimal places, and are
positive. Do not include the dollar sign. Once you are done, press `Enter`. Suppose you deposited 500 dollars:
```aidl
Thank you. $500.00 has been deposited to your account.
Is there anything else you'd like to do?
[D]eposit
[W]ithdraw
[P]rint statement
[Q]uit
```
You are now able to continue with your next action.

### Withdraw - input `w`
If you choose to make a withdrawal, you will be prompted to input an amount to withdraw, as such
```aidl
Please enter the amount to withdraw:
```
At this point, please enter a sum to withdraw. Acceptable values to withdraw have at most 2 decimal places, and are
positive. Overdraft is not allowed, so you may not withdraw more than what you have in your account. Do not include 
the dollar sign. Once you are done, press `Enter`. Suppose you withdrew 100 dollars:
```aidl
Thank you. $100.00 has been withdrawn.
Is there anything else you'd like to do?
[D]eposit
[W]ithdraw
[P]rint statement
[Q]uit
```
You are now able to continue with your next action.

### Print statement - input `p`
If you would like to view your statement, the system will display your account's transaction history. For example:
```aidl
Date                  | Amount  | Balance
8 Jul 2022 11:12:30AM | 500.00  | 500.00
8 Jul 2022 11:14:15AM | -100.00 | 400.00
```
The system will then display the menu again:
```aidl
Is there anything else you'd like to do?
[D]eposit
[W]ithdraw
[P]rint statement
[Q]uit
```
You are now able to continue with your next action.

### Quit - input `q`
If you would like to close the application, the system will display a goodbye message and exit.