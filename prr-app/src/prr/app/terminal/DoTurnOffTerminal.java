package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.IllegalTerminalStatusException;
import prr.exceptions.UnreachableBusyTerminalException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {

    DoTurnOffTerminal(Network context, Terminal terminal) {
        super(Label.POWER_OFF, context, terminal);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.turnOff(_network);
        } catch (IllegalTerminalStatusException e) {
            _display.popup(Message.alreadyOff());
        } catch (UnreachableBusyTerminalException e) {
            // do nothing
        }
    }

}
