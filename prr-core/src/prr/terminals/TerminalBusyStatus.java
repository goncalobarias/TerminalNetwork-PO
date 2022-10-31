package prr.terminals;

import java.io.Serial;

import prr.notifications.BusyToIdleNotification;
import prr.exceptions.IllegalTerminalStatusException;

public class TerminalBusyStatus extends Terminal.Status {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192354L;

    private Terminal.Status _previousStatus;

    public TerminalBusyStatus(Terminal terminal,
      Terminal.Status previousStatus) {
        terminal.super();
        _previousStatus = previousStatus;
    }

    @Override
    protected String getStatusType() {
        return "BUSY";
    }

    @Override
    protected void setOnIdle() throws IllegalTerminalStatusException {
        throw new IllegalTerminalStatusException("IDLE");
    }

    @Override
    protected void setOnSilent() throws IllegalTerminalStatusException {
        throw new IllegalTerminalStatusException("SILENCE");
    }

    @Override
    protected void turnOff() throws IllegalTerminalStatusException {
        throw new IllegalTerminalStatusException("OFF");
    }

    @Override
    protected void setOnBusy() {
        // do nothing
    }

    @Override
    protected void unBusy() {
        // TODO: the previous status implementation does not work with this
        getTerminal().notifyAllClients(
            new BusyToIdleNotification(getTerminal())
        );
        updateStatus(_previousStatus);
    }

    @Override
    protected boolean canStartCommunication() {
        return false;
    }

    @Override
    protected boolean canReceiveTextCommunication() {
        return true;
    }

    @Override
    protected boolean canReceiveInteractiveCommunication() {
        return false;
    }

}
