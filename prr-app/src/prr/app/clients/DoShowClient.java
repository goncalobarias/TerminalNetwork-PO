package prr.app.clients;

import prr.Network;
import prr.app.visitors.ToStringer;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

    private ToStringer toStringer = new ToStringer();

    DoShowClient(Network receiver) {
        super(Label.SHOW_CLIENT, receiver);
        addStringField("clientId", Prompt.key());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            String clientId = stringField("clientId");
            _display.popup(_receiver.getClient(clientId)
                                    .accept(toStringer));
        } catch (prr.exceptions.UnknownClientKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        }
    }

}
