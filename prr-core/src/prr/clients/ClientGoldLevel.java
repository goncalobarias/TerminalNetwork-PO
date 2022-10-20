package prr.clients;

import java.io.Serial;

public class ClientGoldLevel extends Client.Level {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210190314L;

    public ClientGoldLevel(Client client) {
        client.super();
    }

    @Override
    public String getLevelType() {
        return "GOLD";
    }

}
