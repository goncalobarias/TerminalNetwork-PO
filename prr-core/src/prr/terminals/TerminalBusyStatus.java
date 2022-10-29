package prr.terminals;

import java.io.Serial;

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
    protected void setOnIdle() {
        // do nothing
    }

    @Override
    protected void setOnSilent() {
        // do nothing
    }

    @Override
    protected void turnOff() {
        // do nothing
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
