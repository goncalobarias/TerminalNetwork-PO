package prr.app.visitors;

import prr.util.Visitor;
import prr.clients.Client;
import prr.terminals.Terminal;

public class ToStringer extends Visitor<String> {

    @Override
    public String visit(Client client) {
        return "CLIENT" + "|" + client.getId() + "|" + client.getName() + "|" +
                client.getTaxId() + "|" + client.getLevelType() + "|" +
                (client.hasNotificationsEnabled() ? "YES" : "NO") + "|" +
                client.getNumberOfTerminals() + "|" +
                Math.round(client.getPayments()) + "|" +
                Math.round(client.getDebts());
    }

    @Override
    public String visit(Terminal terminal) {
        return terminal.getTerminalType() + "|" +
                terminal.getTerminalId() + "|" + terminal.getClientId() + "|" +
                terminal.getStatusType() + "|" +
                Math.round(terminal.getPayments()) + "|" +
                Math.round(terminal.getDebts()) +
                (terminal.hasFriends() ? "|" + terminal.getFriendsIds() : "");
    }

}
