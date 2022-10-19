package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

    DoOpenMenuTerminalConsole(Network receiver) {
        super(Label.OPEN_MENU_TERMINAL, receiver);
        addStringField("terminalId", Prompt.terminalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            String terminalId = stringField("terminalId");
            (new prr.app.terminal.Menu(_receiver,
                _receiver.getTerminal(terminalId))).open();
        } catch (prr.exceptions.UnknownTerminalKeyException e) {
            throw new UnknownTerminalKeyException(e.getKey());
        }
    }

}
