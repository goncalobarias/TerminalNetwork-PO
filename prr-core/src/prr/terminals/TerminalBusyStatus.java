package prr.terminals;

import java.io.Serial;

import prr.exceptions.IllegalTerminalStatusException;
import prr.exceptions.UnreachableBusyTerminalException;

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
        updateStatus(_previousStatus);
        _previousStatus.unBusy();
    }

    @Override
    protected void sendException() throws UnreachableBusyTerminalException {
        throw new UnreachableBusyTerminalException();
    }

}
