package prr.communications;

import java.io.Serial;

import prr.clients.Client;
import prr.terminals.Terminal;

public class VoiceCommunication extends InteractiveCommunication {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192347L;

    public VoiceCommunication(int id, Terminal terminalReceiver,
      Terminal terminalSender) {
        super(id, terminalReceiver, terminalSender);
    }

    @Override
    public String getCommunicationType() {
        return "VOICE";
    }

    @Override
    public double stopCommunication(int duration) {
        setProgress(false);
        getTerminalReceiver().setOngoingCommunication(null);
        getTerminalReceiver().unBusy();
        getTerminalSender().setOngoingCommunication(null);
        getTerminalSender().unBusy();
        setUnits(duration);
        return computePrice();
    }

    protected double computePrice() {
        Client client = getTerminalSender().getOwner();
        double price = client.getLevel().computePrice(this);
        if (getTerminalSender().isFriend(getTerminalReceiver())) {
            price *= 0.50;
        }
        setPrice(price);
        getTerminalSender().updateBalance(price * -1);
        return price;
    }

}
