package prr.app.terminals;

import prr.Network;
import prr.app.exceptions.DuplicateTerminalKeyException;
import prr.app.exceptions.InvalidTerminalKeyException;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

	DoRegisterTerminal(Network receiver) {
		super(Label.REGISTER_TERMINAL, receiver);
        addIntegerField("terminalId", Prompt.terminalKey());
        addStringField("terminalType", Prompt.terminalType());
        /* while (!stringField("terminalType").equals("BASIC") && 
          !stringField("terminalType").equals("FANCY")) {
            addStringField("terminalType", Prompt.terminalType());
        } */
        //FIXME repeat prompt until we have correct input
        addStringField("clientId", Prompt.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
        try {
            _receiver.registerTerminal(stringField("terminalType"), 
                                        integerField("terminalId"), 
                                        stringField("clientId"), "IDLE");
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
