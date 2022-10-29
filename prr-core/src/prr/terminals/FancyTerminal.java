package prr.terminals;

import java.io.Serial;

import prr.Network;
import prr.clients.Client;
import prr.communications.VideoCommunication;
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
    public void makeVideoCall(String terminalReceiverId, Network context)
      throws UnsupportedCommunicationAtDestinationException,
      UnknownTerminalKeyException, UnreachableOffTerminalException,
      UnreachableBusyTerminalException, UnreachableSilentTerminalException {
        if (canStartCommunication()) {
            Terminal receiver = context.getTerminal(terminalReceiverId);
            receiver.receiveVideoCall(this, context);
        }
    }

    @Override
    protected void receiveVideoCall(Terminal sender, Network context)
      throws UnreachableOffTerminalException, UnreachableBusyTerminalException,
      UnreachableSilentTerminalException {
        if (canReceiveInteractiveCommunication()) {
            setOnBusy();
            int newId = context.getNextCommunicationId();
            VideoCommunication communication =
                new VideoCommunication(newId, this, sender);
            receiveCommunication(communication);
            setOngoingCommunication(communication);
        } else {
            addToNotify(sender.getOwner());
            // TODO: fix this horrible mess of exceptions
            switch (getStatusType()) {
                case "BUSY" -> throw new UnreachableBusyTerminalException();
                case "OFF" -> throw new UnreachableOffTerminalException();
                case "SILENCE" -> throw new UnreachableSilentTerminalException();
                default -> throw new UnreachableBusyTerminalException();
            }
        }
    }

}
