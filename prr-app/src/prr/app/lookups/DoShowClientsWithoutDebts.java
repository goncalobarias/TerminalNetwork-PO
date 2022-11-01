package prr.app.lookups;

import prr.Network;
import prr.app.visitors.ToStringer;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show clients with positive balance.
 */
class DoShowClientsWithoutDebts extends Command<Network> {

    DoShowClientsWithoutDebts(Network receiver) {
        super(Label.SHOW_CLIENTS_WITHOUT_DEBTS, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        ToStringer toStringer = new ToStringer();
        _receiver.getClientsWithoutDebts()
                .stream()
                .map(o -> o.accept(toStringer))
                .forEach(_display::popup);
    }

}
