package prr.app.lookups;

import prr.Network;
import prr.app.visitors.RenderClient;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show clients with negative balance.
 */
class DoShowClientsWithDebts extends Command<Network> {

    DoShowClientsWithDebts(Network receiver) {
        super(Label.SHOW_CLIENTS_WITH_DEBTS, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        RenderClient _renderer = new RenderClient();
        _receiver.getClientsWithDebts()
                .stream()
                .map(c -> c.accept(_renderer))
                .forEach(_display::popup);
    }

}
