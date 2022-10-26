package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

    DoRemoveFriend(Network context, Terminal terminal) {
        super(Label.REMOVE_FRIEND, context, terminal);
        addStringField("terminalFriendId", Prompt.terminalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            String terminalFriendId = stringField("terminalFriendId");
            _receiver.removeFriend(terminalFriendId, _network);
        } catch (prr.exceptions.UnknownTerminalKeyException e) {
            throw new UnknownTerminalKeyException(e.getKey());
        }
    }

}
