package prr.clients;

import java.io.Serial;

public class ClientPlatinumLevel extends Client.Level {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192343L;

    public ClientPlatinumLevel(Client client) {
        client.super();
    }

    @Override
    protected String getLevelType() {
        return "PLATINUM";
    }

}
