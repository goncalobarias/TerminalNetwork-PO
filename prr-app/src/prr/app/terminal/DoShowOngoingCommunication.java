package prr.app.terminal;

import prr.Network;
import prr.app.visitors.RenderCommunication;
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
            RenderCommunication renderer = new RenderCommunication();
            _display.popup(_receiver.getOngoingCommunication()
                                    .accept(renderer));
        } catch (InvalidCommunicationException e) {
            _display.popup(Message.noOngoingCommunication());
        }
    }

}
