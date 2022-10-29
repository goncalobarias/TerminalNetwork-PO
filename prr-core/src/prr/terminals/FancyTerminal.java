package prr.terminals;

import java.io.Serial;

import prr.Network;
import prr.clients.Client;
import prr.communications.InteractiveCommunication;
import prr.communications.VideoCommunication;
import prr.exceptions.UnknownTerminalKeyException;

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
    public void makeVideoCall(String terminalReceiverId, Network context)
      throws UnknownTerminalKeyException {
        Terminal receiver = context.getTerminal(terminalReceiverId);
        receiver.receiveVideoCall(this, context);
    }

    @Override
    protected void receiveVideoCall(Terminal sender, Network context) {
        if (canReceiveInteractiveCommunication()) {
            int newId = context.getNextCommunicationId();
            InteractiveCommunication communication =
                new VideoCommunication(newId, this, sender);
            addCommunication(communication);
            setOngoingCommunication(communication);
        } else {
            addToNotify(sender.getOwner());
        }
    }

}
