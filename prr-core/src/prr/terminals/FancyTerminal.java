package prr.terminals;

import prr.clients.Client;

public class FancyTerminal extends Terminal {

    public FancyTerminal(String idTerminal, Client owner) {
        super(idTerminal, owner);
    }

    @Override
    public String getTerminalType() {
        return "FANCY";
    }

}
