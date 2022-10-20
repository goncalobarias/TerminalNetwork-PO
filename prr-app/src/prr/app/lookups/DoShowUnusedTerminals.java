package prr.app.lookups;

import prr.Network;
import prr.app.visitors.ToStringer;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show unused terminals (without communications).
 */
class DoShowUnusedTerminals extends Command<Network> {

    private ToStringer toStringer = new ToStringer();

    DoShowUnusedTerminals(Network receiver) {
        super(Label.SHOW_UNUSED_TERMINALS, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        _receiver.getUnusedTerminals()
                .stream()
                .map(t -> t.accept(toStringer))
                .forEach(_display::popup);
    }

}
