package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show balance.
 */
class DoShowTerminalBalance extends TerminalCommand {

    DoShowTerminalBalance(Network context, Terminal terminal) {
        super(Label.SHOW_BALANCE, context, terminal);
    }

    @Override
    protected final void execute() throws CommandException {
        String terminalId = _receiver.getTerminalId();
        long terminalPayments = Math.round(_receiver.getPayments());
        long terminalDebts = Math.round(_receiver.getDebts());
        _display.popup(
            Message.terminalPaymentsAndDebts(
                terminalId, terminalPayments, terminalDebts
            )
        );
    }

}
