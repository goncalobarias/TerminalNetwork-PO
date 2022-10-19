package prr.terminals;

import prr.clients.Client;

public class BasicTerminal extends Terminal {

    public BasicTerminal(String idTerminal, Client owner) {
        super(idTerminal, owner);
    }

    @Override
    public String getTerminalType() {
        return "BASIC";
    }

}
