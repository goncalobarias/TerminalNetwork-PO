package prr.app.lookups;

import prr.Network;
import prr.app.visitors.RenderCommunication;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show communications from a client.
 */
class DoShowCommunicationsFromClient extends Command<Network> {

    DoShowCommunicationsFromClient(Network receiver) {
        super(Label.SHOW_COMMUNICATIONS_FROM_CLIENT, receiver);
        addStringField("clientId", Prompt.clientKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            RenderCommunication _renderer = new RenderCommunication();
            String clientId = stringField("clientId");
            _receiver.getAllCommunicationsMadeByClient(clientId)
                    .stream()
                    .map(c -> c.accept(_renderer))
                    .forEach(_display::popup);
        } catch (prr.exceptions.UnknownClientKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        }
    }

}
