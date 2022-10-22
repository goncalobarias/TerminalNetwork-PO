package prr.app.terminals;

import prr.Network;
import prr.app.visitors.RenderTerminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all terminals.
 */
class DoShowAllTerminals extends Command<Network> {

    DoShowAllTerminals(Network receiver) {
        super(Label.SHOW_ALL_TERMINALS, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        RenderTerminal _renderer = new RenderTerminal();
        _receiver.getAllTerminals()
                .stream()
                .map(t -> t.accept(_renderer))
                .forEach(_display::popup);
    }

}
