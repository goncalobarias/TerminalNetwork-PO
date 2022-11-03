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
        addStringField("terminalId", Prompt.terminalKey());
        addOptionField("terminalType", Prompt.terminalType(), "BASIC", "FANCY");
        addStringField("clientId", Prompt.clientKey());
    }

    @Override
    protected final void execute() throws CommandException {
        String terminalType = optionField("terminalType");
        String terminalId = stringField("terminalId");
        String clientId = stringField("clientId");
        try {
            _receiver.registerTerminal(terminalType, terminalId, clientId);
        } catch (prr.exceptions.UnknownClientKeyException e) {
            throw new UnknownClientKeyException(e.getKey());
        } catch (prr.exceptions.InvalidTerminalKeyException e) {
            throw new InvalidTerminalKeyException(e.getKey());
        } catch (prr.exceptions.DuplicateTerminalKeyException e) {
            throw new DuplicateTerminalKeyException(e.getKey());
        } catch (prr.exceptions.UnknownEntryTypeException e) {
            e.printStackTrace();
        }
    }

}
