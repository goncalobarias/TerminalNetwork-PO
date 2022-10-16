package prr.terminals;

import prr.clients.Client;

public class FancyTerminal extends Terminal {

    public FancyTerminal(int idTerminal, Client owner, String status) {
        super(idTerminal, owner, status);
    }

    @Override
    public String getTerminalType() {
        return "FANCY";
    }

}
