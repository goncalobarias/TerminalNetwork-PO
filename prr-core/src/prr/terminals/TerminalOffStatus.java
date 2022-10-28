package prr.terminals;

import java.io.Serial;

import prr.exceptions.IllegalTerminalStatusException;

public class TerminalOffStatus extends Terminal.Status {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192357L;

    public TerminalOffStatus(Terminal terminal) {
        terminal.super();
    }

    @Override
    protected String getStatusType() {
        return "OFF";
    }

    @Override
    protected void setOnIdle() {
        updateStatus(new TerminalIdleStatus(getTerminal()));
    }

    @Override
    protected void setOnSilent() {
        updateStatus(new TerminalSilentStatus(getTerminal()));
    }

    @Override
    protected void turnOff() throws IllegalTerminalStatusException {
        throw new IllegalTerminalStatusException(getStatusType());
    }

}
