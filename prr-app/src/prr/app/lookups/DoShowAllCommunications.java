package prr.app.lookups;

import prr.Network;
import prr.app.visitors.ToStringer;
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
        ToStringer toStringer = new ToStringer();
        _receiver.getAllCommunications()
                .stream()
                .map(o -> o.accept(toStringer))
                .forEach(_display::popup);
    }

}
