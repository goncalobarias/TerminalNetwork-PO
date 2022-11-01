package prr.app.lookups;

import prr.Network;
import prr.app.visitors.ToStringer;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show terminals with positive balance.
 */
class DoShowTerminalsWithPositiveBalance extends Command<Network> {

    DoShowTerminalsWithPositiveBalance(Network receiver) {
        super(Label.SHOW_TERMINALS_WITH_POSITIVE_BALANCE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        ToStringer toStringer = new ToStringer();
        _receiver.getTerminalsWithPositiveBalance()
                .stream()
                .map(o -> o.accept(toStringer))
                .forEach(_display::popup);
    }

}
