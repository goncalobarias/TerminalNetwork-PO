package prr.app.lookups;

import prr.Network;
import prr.app.util.ToStringer;
import prr.app.util.Comparinator;
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
                .sorted(Comparinator.CLIENT_DEBT_COMPARATOR)
                .map(o -> o.accept(toStringer))
                .forEach(_display::popup);
    }

}
