package prr.app.clients;

import prr.Network;
import prr.app.util.ToStringer;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

    DoShowAllClients(Network receiver) {
        super(Label.SHOW_ALL_CLIENTS, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        ToStringer toStringer = new ToStringer();
        _receiver.getAllClients()
                .stream()
                .map(o -> o.accept(toStringer))
                .forEach(_display::popup);
    }

}
