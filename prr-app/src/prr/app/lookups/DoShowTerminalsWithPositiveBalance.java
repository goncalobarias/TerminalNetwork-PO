package prr.app.lookups;

import prr.Network;
import prr.app.visitors.RenderTerminal;
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
        RenderTerminal _renderer = new RenderTerminal();
        _receiver.getTerminalsWithPositiveBalance()
                .stream()
                .map(o -> o.accept(_renderer))
                .forEach(_display::popup);
    }

}
