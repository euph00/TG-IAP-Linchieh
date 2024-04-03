package main.commands.transactions;

import main.commands.Command;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Abstract representation of actions involving monetary changes
 */
public abstract class Transaction extends Command {

    public BigDecimal amount;
    public Date datetime;

}
