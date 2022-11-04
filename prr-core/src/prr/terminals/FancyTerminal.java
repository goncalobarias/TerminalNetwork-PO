package prr.terminals;

import java.io.Serial;

import prr.Network;
import prr.clients.Client;
import prr.communications.VideoCommunication;
import prr.exceptions.InvalidCommunicationException;
import prr.exceptions.UnknownTerminalKeyException;
import prr.exceptions.UnreachableBusyTerminalException;
import prr.exceptions.UnreachableOffTerminalException;
import prr.exceptions.UnreachableSilentTerminalException;
import prr.exceptions.UnsupportedCommunicationAtDestinationException;

public class FancyTerminal extends Terminal {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192352L;

    public FancyTerminal(String idTerminal, Client owner) {
        super(idTerminal, owner);
    }

    @Override
    public String getTerminalType() {
        return "FANCY";
    }

    @Override
    public void makeVideoCall(String terminalReceiverId, Network network)
      throws UnsupportedCommunicationAtDestinationException,
      UnknownTerminalKeyException, UnreachableOffTerminalException,
      UnreachableBusyTerminalException, UnreachableSilentTerminalException,
      InvalidCommunicationException {
        if (terminalReceiverId.equals(getTerminalId())) {
            throw new InvalidCommunicationException();
        }
        if (canStartCommunication()) {
            network.changed();
            Terminal receiver = network.getTerminal(terminalReceiverId);
            receiver.receiveVideoCall(this, network);
        }
    }

    @Override
    protected void receiveVideoCall(Terminal sender, Network network)
      throws UnreachableOffTerminalException, UnreachableBusyTerminalException,
      UnreachableSilentTerminalException {
        assertInteractiveCommunicationReception(sender.getOwner());
        int newId = network.getNextCommunicationId();
        VideoCommunication communication =
            new VideoCommunication(newId, this, sender);
        network.registerCommunication(communication);
    }

}
