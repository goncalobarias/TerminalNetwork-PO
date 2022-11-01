package prr.app.terminal;

import prr.Network;
import prr.app.visitors.ToStringer;
import prr.terminals.Terminal;
import prr.exceptions.InvalidCommunicationException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {

    DoShowOngoingCommunication(Network context, Terminal terminal) {
        super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            ToStringer toStringer = new ToStringer();
            _display.popup(_receiver.getOngoingCommunication()
                                    .accept(toStringer));
        } catch (InvalidCommunicationException e) {
            _display.popup(Message.noOngoingCommunication());
        }
    }

}
