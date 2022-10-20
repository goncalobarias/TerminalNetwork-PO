package prr.terminals;

import java.io.Serial;

import prr.clients.Client;

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

}
