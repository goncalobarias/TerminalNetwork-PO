package prr.terminals;

import java.io.Serial;

import prr.clients.Client;
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
    protected void assertTextCommunicationReception(Client clientToNotify) {
        // do nothing
    }

    @Override
    protected void assertInteractiveCommunicationReception(
      Client clientToNotify) throws UnreachableSilentTerminalException {
        getTerminal().addToNotify(clientToNotify);
        throw new UnreachableSilentTerminalException();
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

}
