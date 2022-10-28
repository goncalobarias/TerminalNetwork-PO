package prr.app.clients;

import prr.Network;
import prr.app.visitors.RenderClient;
import prr.app.visitors.RenderNotification;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

    DoShowClient(Network receiver) {
        super(Label.SHOW_CLIENT, receiver);
        addStringField("clientId", Prompt.key());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            RenderClient clientRenderer = new RenderClient();
            RenderNotification notifRenderer = new RenderNotification();
            String clientId = stringField("clientId");
            _display.popup(_receiver.getClient(clientId)
                                    .accept(clientRenderer));
            _receiver.getClientNotifications(clientId)
                    .stream()
                    .map(o -> o.accept(notifRenderer))
                    .forEach(_display::popup);
        } catch (prr.exceptions.UnknownClientKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        }
    }

}
