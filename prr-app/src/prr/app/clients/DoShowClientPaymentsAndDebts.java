package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

	DoShowClientPaymentsAndDebts(Network receiver) {
		super(Label.SHOW_CLIENT_BALANCE, receiver);
        addStringField("clientId", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
        String clientId = stringField("clientId");
        try {
            long clientPayments =
                Math.round(_receiver.getClientPayments(clientId));
            long clientDebts =
                Math.round(_receiver.getClientDebts(clientId));
            _display.popup(
                Message.clientPaymentsAndDebts(
                    clientId, clientPayments, clientDebts
                )
            );
        } catch (prr.exceptions.UnknownClientKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        }
    }

}
