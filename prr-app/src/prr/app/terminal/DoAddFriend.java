package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

    DoAddFriend(Network context, Terminal terminal) {
        super(Label.ADD_FRIEND, context, terminal);
        addStringField("terminalFriendId", Prompt.terminalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            String terminalFriendId = stringField("terminalFriendId");
            _receiver.addFriend(_network.getTerminal(terminalFriendId));
        } catch (prr.exceptions.UnknownTerminalKeyException e) {
            throw new UnknownTerminalKeyException(e.getKey());
        }
    }

}
