package prr.terminals;

import prr.clients.Client;

public class BasicTerminal extends Terminal {

    public BasicTerminal(int idTerminal, Client owner, String status) {
        super(idTerminal, owner, status);
    }

    @Override
    public String getTerminalType() {
        return "BASIC";
    }

}
