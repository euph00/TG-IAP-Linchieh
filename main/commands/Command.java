package main.commands;

import main.models.Account;

/**
 * Abstract representation of some action on accounts
 */
public abstract class Command {

    public String action;

    abstract public Record execute(Account account);
}
