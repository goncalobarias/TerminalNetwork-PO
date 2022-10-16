package prr.clients;

public class ClientGoldLevel extends Client.Level {

    public ClientGoldLevel(Client client) {
        client.super();
    }

    @Override
    public String getLevelType() {
        return "GOLD";
    }

}
