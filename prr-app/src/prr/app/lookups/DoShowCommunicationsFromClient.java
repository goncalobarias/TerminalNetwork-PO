package prr.app.lookups;

import prr.Network;
import prr.app.visitors.ToStringer;
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
        ToStringer toStringer = new ToStringer();
        String clientId = stringField("clientId");
        try {
            _receiver.getAllCommunicationsMadeByClient(clientId)
                    .stream()
                    .map(o -> o.accept(toStringer))
                    .forEach(_display::popup);
        } catch (prr.exceptions.UnknownClientKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        }
    }

}
