package prr.app.visitors;

import prr.util.ClientVisitor;
import prr.clients.Client;

public class RenderClient implements ClientVisitor {

    @Override
    public String visit(Client client) {
        return "CLIENT" + "|" + client.getId() + "|" + client.getName() + "|" +
                client.getTaxId() + "|" + client.getLevelType() + "|" +
                (client.hasNotificationsEnabled() ? "YES" : "NO") + "|" +
                client.getNumberOfTerminals() + "|" +
                Math.round(client.getPayments()) + "|" +
                Math.round(client.getDebts());
    }

}
