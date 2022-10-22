package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

    DoDisableClientNotifications(Network receiver) {
        super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
        addStringField("clientId", Prompt.key());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            String clientId = stringField("clientId");
            _receiver.disableClientNotifications(clientId);
        } catch (prr.exceptions.UnknownClientKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        } catch (prr.exceptions.NotificationsAlreadyToggledException e) {
            _display.popup(Message.clientNotificationsAlreadyDisabled());
        }
    }

}
