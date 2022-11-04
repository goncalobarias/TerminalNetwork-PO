package prr.terminals;

import java.io.Serial;

import prr.Network;
import prr.clients.Client;
import prr.exceptions.UnsupportedCommunicationAtOriginException;
import prr.exceptions.UnsupportedCommunicationAtDestinationException;

public class BasicTerminal extends Terminal {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192351L;

    public BasicTerminal(String idTerminal, Client owner) {
        super(idTerminal, owner);
    }

    @Override
    public String getTerminalType() {
        return "BASIC";
    }

    @Override
    public void makeVideoCall(String terminalReceiverId, Network network)
      throws UnsupportedCommunicationAtOriginException {
        throw new UnsupportedCommunicationAtOriginException();
    }

    @Override
    protected void receiveVideoCall(Terminal sender, Network network)
      throws UnsupportedCommunicationAtDestinationException {
        throw new UnsupportedCommunicationAtDestinationException();
    }

}
