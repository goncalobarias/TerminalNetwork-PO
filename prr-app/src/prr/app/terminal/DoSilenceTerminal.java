package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.IllegalTerminalStatusException;
import prr.exceptions.UnreachableBusyTerminalException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {

    DoSilenceTerminal(Network context, Terminal terminal) {
        super(Label.MUTE_TERMINAL, context, terminal);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.setOnSilent();
        } catch (IllegalTerminalStatusException e) {
            _display.popup(Message.alreadySilent());
        } catch (UnreachableBusyTerminalException e) {
            // do nothing
        }
    }

}
