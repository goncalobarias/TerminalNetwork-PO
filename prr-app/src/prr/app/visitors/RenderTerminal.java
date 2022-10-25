package prr.app.visitors;

import prr.util.TerminalVisitor;
import prr.terminals.Terminal;

public class RenderTerminal implements TerminalVisitor {

    @Override
    public String visit(Terminal terminal) {
        return terminal.getTerminalType() + "|" +
                terminal.getTerminalId() + "|" +
                terminal.getClientId() + "|" +
                terminal.getStatusType() + "|" +
                Math.round(terminal.getPayments()) + "|" +
                Math.round(terminal.getDebts()) +
                (terminal.hasFriends() ? "|" + terminal.getFriendsIds() : "");
    }

}
