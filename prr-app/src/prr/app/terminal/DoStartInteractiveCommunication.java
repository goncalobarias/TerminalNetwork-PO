package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.exceptions.InvalidCommunicationException;
import prr.exceptions.UnreachableBusyTerminalException;
import prr.exceptions.UnreachableOffTerminalException;
import prr.exceptions.UnreachableSilentTerminalException;
import prr.exceptions.UnsupportedCommunicationAtOriginException;
import prr.exceptions.UnsupportedCommunicationAtDestinationException;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

    DoStartInteractiveCommunication(Network context, Terminal terminal) {
        super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
        addStringField("terminalId", Prompt.terminalKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String terminalId = stringField("terminalId");
        String commType =
            Form.requestOption(Prompt.commType(), "VIDEO", "VOICE");
        try {
            if (commType.equals("VIDEO")) {
                _receiver.makeVideoCall(terminalId, _network);
            } else {
                _receiver.makeVoiceCall(terminalId, _network);
            }
        } catch (UnsupportedCommunicationAtOriginException e) {
            _display.popup(Message.unsupportedAtOrigin(
                _receiver.getTerminalId(), commType
            ));
        } catch (UnsupportedCommunicationAtDestinationException e) {
            _display.popup(Message.unsupportedAtDestination(
                terminalId, commType
            ));
        } catch (InvalidCommunicationException e) {
            // do nothing
        } catch (prr.exceptions.UnknownTerminalKeyException e) {
            throw new UnknownTerminalKeyException(e.getKey());
        } catch (UnreachableOffTerminalException e) {
            _display.popup(Message.destinationIsOff(terminalId));
        } catch (UnreachableBusyTerminalException e) {
            _display.popup(Message.destinationIsBusy(terminalId));
        } catch (UnreachableSilentTerminalException e) {
            _display.popup(Message.destinationIsSilent(terminalId));
        }
    }

}
