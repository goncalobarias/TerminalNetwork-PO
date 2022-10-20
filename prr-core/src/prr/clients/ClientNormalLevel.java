package prr.clients;

import java.io.Serial;

public class ClientNormalLevel extends Client.Level {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192342L;

    public ClientNormalLevel(Client client) {
        client.super();
    }

    @Override
    public String getLevelType() {
        return "NORMAL";
    }

}
