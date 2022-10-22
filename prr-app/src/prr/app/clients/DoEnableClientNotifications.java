package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

    DoEnableClientNotifications(Network receiver) {
        super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
        addStringField("clientId", Prompt.key());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            String clientId = stringField("clientId");
            _receiver.enableClientNotifications(clientId);
        } catch (prr.exceptions.UnknownClientKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        } catch (prr.exceptions.NotificationsAlreadyToggledException e) {
            _display.popup(Message.clientNotificationsAlreadyEnabled());
        }
    }

}
