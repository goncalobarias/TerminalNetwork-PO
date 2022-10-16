package prr.clients;

public class ClientNormalLevel extends Client.Level {

    public ClientNormalLevel(Client client) {
        client.super();
    }

    @Override
    public String getLevelType() {
        return "NORMAL";
    }

}
