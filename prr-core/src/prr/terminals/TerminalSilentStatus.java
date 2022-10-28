package prr.terminals;

import java.io.Serial;

import prr.exceptions.IllegalTerminalStatusException;

public class TerminalSilentStatus extends Terminal.Status {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192358L;

    public TerminalSilentStatus(Terminal terminal) {
        terminal.super();
    }

    @Override
    protected String getStatusType() {
        return "SILENCE";
    }

    @Override
    protected void setOnIdle() {
        updateStatus(new TerminalIdleStatus(getTerminal()));
    }

    @Override
    protected void setOnSilent() throws IllegalTerminalStatusException {
        throw new IllegalTerminalStatusException(getStatusType());
    }

    @Override
    protected void turnOff() {
        updateStatus(new TerminalOffStatus(getTerminal()));
    }

}
