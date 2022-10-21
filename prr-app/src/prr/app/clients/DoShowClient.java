package prr.app.clients;

import prr.Network;
import prr.app.visitors.RenderClient;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

    private RenderClient _renderer = new RenderClient();

    DoShowClient(Network receiver) {
        super(Label.SHOW_CLIENT, receiver);
        addStringField("clientId", Prompt.key());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            String clientId = stringField("clientId");
            _display.popup(_receiver.getClient(clientId)
                                    .accept(_renderer));
        } catch (prr.exceptions.UnknownClientKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        }
    }

}
