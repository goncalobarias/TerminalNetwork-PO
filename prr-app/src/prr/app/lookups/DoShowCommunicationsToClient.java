package prr.app.lookups;

import prr.Network;
import prr.app.visitors.RenderCommunication;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

    DoShowCommunicationsToClient(Network receiver) {
        super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
        addStringField("clientId", Prompt.clientKey());
    }

    @Override
    protected final void execute() throws CommandException {
        RenderCommunication _renderer = new RenderCommunication();
        String clientId = stringField("clientId");
        _receiver.getAllCommunicationsReceivedByClient(clientId)
                .stream()
                .map(c -> c.accept(_renderer))
                .forEach(_display::popup);
    }

}
