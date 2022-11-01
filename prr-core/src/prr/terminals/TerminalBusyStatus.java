package prr.terminals;

import java.io.Serial;

import prr.clients.Client;
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
    protected void assertTextCommunicationReception(Client clientToNotify) {
        // do nothing
    }

    @Override
    protected void assertInteractiveCommunicationReception(
      Client clientToNotify) throws UnreachableBusyTerminalException {
        getTerminal().addToNotify(clientToNotify);
        throw new UnreachableBusyTerminalException();
    }

    @Override
    protected void setOnIdle() throws UnreachableBusyTerminalException {
        throw new UnreachableBusyTerminalException();
    }

    @Override
    protected void setOnSilent() throws UnreachableBusyTerminalException {
        throw new UnreachableBusyTerminalException();
    }

    @Override
    protected void turnOff() throws UnreachableBusyTerminalException {
        throw new UnreachableBusyTerminalException();
    }

    @Override
    protected void setOnBusy() {
        // do nothing
    }

    @Override
    protected void unBusy() {
        updateStatus(_previousStatus);
        _previousStatus.unBusy(); // TODO: see if storing the previous state is good practice
    }

}
