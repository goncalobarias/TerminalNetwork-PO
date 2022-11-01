package prr.app.lookups;

import prr.Network;
import prr.clients.Client;
import prr.app.visitors.ToStringer;
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
        ToStringer toStringer = new ToStringer();
        _receiver.getClientsWithDebts()
                .stream()
                .sorted(Client.DEBT_COMPARATOR) // TODO: make sure this doesn't break the core/app separation and make sure this is sorting correctly
                .map(o -> o.accept(toStringer))
                .forEach(_display::popup);
    }

}
