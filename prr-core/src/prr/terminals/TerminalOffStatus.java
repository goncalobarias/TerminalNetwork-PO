package prr.terminals;

import java.io.Serial;

import prr.notifications.OffToIdleNotification;
import prr.notifications.OffToSilentNotification;
import prr.exceptions.IllegalTerminalStatusException;
import prr.exceptions.UnreachableOffTerminalException;

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
    protected boolean canStartCommunication() {
        return false;
    }

    @Override
    protected boolean canReceiveTextCommunication() {
        return false;
    }

    @Override
    protected boolean canReceiveInteractiveCommunication() {
        return false;
    }

    @Override
    protected void setOnIdle() {
        getTerminal().notifyAllClients(
            new OffToIdleNotification(getTerminal())
        );
        updateStatus(new TerminalIdleStatus(getTerminal()));
    }

    @Override
    protected void setOnSilent() {
        getTerminal().notifyAllClients(
            new OffToSilentNotification(getTerminal())
        );
        updateStatus(new TerminalSilentStatus(getTerminal()));
    }

    @Override
    protected void turnOff() throws IllegalTerminalStatusException {
        throw new IllegalTerminalStatusException(getStatusType());
    }

    @Override
    protected void setOnBusy() {
        // do nothing
    }

    @Override
    protected void unBusy() {
        // do nothing
    }

    @Override
    protected void sendException() throws UnreachableOffTerminalException {
        throw new UnreachableOffTerminalException();
    }

}
