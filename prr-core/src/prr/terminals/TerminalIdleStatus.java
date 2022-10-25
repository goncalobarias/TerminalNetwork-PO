package prr.terminals;

import java.io.Serial;

import prr.exceptions.TerminalStatusAlreadySetException;

public class TerminalIdleStatus extends Terminal.Status {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192356L;

    public TerminalIdleStatus(Terminal terminal) {
        terminal.super();
    }

    @Override
    protected String getStatusType() {
        return "IDLE";
    }

    @Override
    protected void setOnIdle() throws TerminalStatusAlreadySetException {
        throw new TerminalStatusAlreadySetException(getStatusType());
    }

    @Override
    protected void setOnSilent() {
        updateStatus(new TerminalSilentStatus(getTerminal()));
    }

    @Override
    protected void turnOff() {
        updateStatus(new TerminalOffStatus(getTerminal()));
    }

}
