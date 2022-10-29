package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.UnreachableOffTerminalException;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

    DoSendTextCommunication(Network context, Terminal terminal) {
        super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
        addStringField("terminalId", Prompt.terminalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String terminalId = stringField("terminalId");
        try {
            _receiver.sendSMS(terminalId, _network,
                Form.requestString(Prompt.textMessage()));
        } catch (prr.exceptions.UnknownTerminalKeyException e) {
            throw new UnknownTerminalKeyException(e.getKey());
        } catch (UnreachableOffTerminalException e) {
            _display.popup(Message.destinationIsOff(terminalId));
        }
    }

}
