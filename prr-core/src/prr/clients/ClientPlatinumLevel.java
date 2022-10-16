package prr.clients;

public class ClientPlatinumLevel extends Client.Level {

    public ClientPlatinumLevel(Client client) {
        client.super();
    }

    @Override
    public String getLevelType() {
        return "PLATINUM";
    }

}
