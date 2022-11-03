package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.InvalidCommunicationException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

    DoPerformPayment(Network context, Terminal terminal) {
        super(Label.PERFORM_PAYMENT, context, terminal);
        addIntegerField("communicationId", Prompt.commKey());
    }

    @Override
    protected final void execute() throws CommandException {
        int communicationId = integerField("communicationId");
        try {
            _receiver.performPayment(communicationId, _network);
        } catch (InvalidCommunicationException e) {
            _display.popup(Message.invalidCommunication());
        }
    }

}
