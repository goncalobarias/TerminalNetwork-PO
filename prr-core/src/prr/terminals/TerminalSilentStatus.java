package prr.terminals;

import java.io.Serial;

import prr.notifications.SilentToIdleNotification;
import prr.exceptions.IllegalTerminalStatusException;
import prr.exceptions.UnreachableSilentTerminalException;

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
    protected boolean canStartCommunication() {
        return true;
    }

    @Override
    protected boolean canReceiveTextCommunication() {
        return true;
    }

    @Override
    protected boolean canReceiveInteractiveCommunication() {
        return false;
    }

    @Override
    protected void setOnIdle() {
        getTerminal().notifyAllClients(
            new SilentToIdleNotification(getTerminal())
        );
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

    @Override
    protected void setOnBusy() {
        updateStatus(new TerminalBusyStatus(getTerminal(), this));
    }

    @Override
    protected void unBusy() {
        // do nothing
    }

    @Override
    protected void sendException() throws UnreachableSilentTerminalException {
        throw new UnreachableSilentTerminalException();
    }

}
