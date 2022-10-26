package prr.app.lookups;

import prr.Network;
import prr.app.visitors.RenderCommunication;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for showing all communications.
 */
class DoShowAllCommunications extends Command<Network> {

    DoShowAllCommunications(Network receiver) {
        super(Label.SHOW_ALL_COMMUNICATIONS, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        RenderCommunication _renderer = new RenderCommunication();
        _receiver.getAllCommunications()
                .stream()
                .map(o -> o.accept(_renderer))
                .forEach(_display::popup);
    }

}
