package prr.app.clients;

import prr.Network;
import prr.app.visitors.RenderClient;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

    private RenderClient _renderer = new RenderClient();

    DoShowAllClients(Network receiver) {
        super(Label.SHOW_ALL_CLIENTS, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        _receiver.getAllClients()
                .stream()
                .map(c -> c.accept(_renderer))
                .forEach(_display::popup);
    }

}
