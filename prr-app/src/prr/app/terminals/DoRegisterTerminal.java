package prr.app.terminals;

import java.util.List;
import java.util.Arrays;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
	}

	@Override
	protected final void execute() throws CommandException {
        try {
            int terminalId = Form.requestInteger(Prompt.terminalKey());
            List<String> validTypes = Arrays.asList("BASIC", "FANCY");
            String type = "";
            do {
                type = Form.requestString(Prompt.terminalType());
            } while (!validTypes.contains(type));
            String clientId = Form.requestString(Prompt.clientKey());

            _receiver.registerTerminal(type, terminalId, clientId, "IDLE");
        } catch (prr.exceptions.InvalidTerminalKeyException e) {
            throw new InvalidTerminalKeyException(e.getKey());
        } catch (prr.exceptions.DuplicateTerminalKeyException e) {
            throw new DuplicateTerminalKeyException(e.getKey());
        } catch (prr.exceptions.UnknownClientKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        } catch (prr.exceptions.UnknownEntryTypeException e) {
            e.printStackTrace();
        }
	}

}
