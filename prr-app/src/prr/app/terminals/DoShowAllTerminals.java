package prr.app.terminals;

import prr.Network;
import prr.app.visitors.ToStringer;
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
        ToStringer toStringer = new ToStringer();
        _receiver.getAllTerminals()
                .stream()
                .map(o -> o.accept(toStringer))
                .forEach(_display::popup);
    }

}
