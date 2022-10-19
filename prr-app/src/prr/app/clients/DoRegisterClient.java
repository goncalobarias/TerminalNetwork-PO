package prr.app.clients;

import prr.Network;
import prr.app.exceptions.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

    DoRegisterClient(Network receiver) {
        super(Label.REGISTER_CLIENT, receiver);
        addStringField("clientId", Prompt.key());
        addStringField("clientName", Prompt.name());
        addIntegerField("taxId", Prompt.taxId());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            String clientId = stringField("clientId");
            String clientName = stringField("clientName");
            int taxId = integerField("taxId");
            _receiver.registerClient(clientId, clientName, taxId);
        } catch (prr.exceptions.DuplicateClientKeyException e) {
            throw new DuplicateClientKeyException(e.getKey());
        }
    }

}
