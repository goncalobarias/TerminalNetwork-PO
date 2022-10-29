package prr.terminals;

import java.io.Serial;

import prr.exceptions.IllegalTerminalStatusException;

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
    protected void setOnIdle() throws IllegalTerminalStatusException {
        throw new IllegalTerminalStatusException(getStatusType());
    }

    @Override
    protected void setOnSilent() {
        updateStatus(new TerminalSilentStatus(getTerminal()));
    }

    @Override
    protected void turnOff() {
        updateStatus(new TerminalOffStatus(getTerminal()));
    }

    protected void setOnBusy() {
        updateStatus(new TerminalBusyStatus(getTerminal(), this));
    }

    @Override
    protected boolean canStartCommunication() {
        return true;
    }

    @Override
    protected boolean canReceiveTextCommunication() {
        return true;
    }

    @Override
    protected boolean canReceiveInteractiveCommunication() {
        return true;
    }


}
