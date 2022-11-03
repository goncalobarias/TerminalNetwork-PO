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
        String terminalFriendId = stringField("terminalFriendId");
        try {
            _receiver.addFriend(terminalFriendId, _network);
        } catch (prr.exceptions.UnknownTerminalKeyException e) {
            throw new UnknownTerminalKeyException(e.getKey());
        } catch (prr.exceptions.InvalidFriendException e) {
            // do nothing
        }
    }

}
