package prr.terminals;

import java.io.Serial;

import prr.Network;
import prr.clients.Client;

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
    public void makeVideoCall(String terminalReceiverId, Network context) {
        // TODO: throw exception because origin is not supported
    }

    @Override
    protected void receiveVideoCall(Terminal sender, Network context) {
        // TODO: throw exception because destination is not supported
    }

}
