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
    public double finishCommunication(int duration) {
        finishInteractiveCommunication(duration);
        getTerminalSender().getOwner()
            .resetNumberOfConsecutiveCommunications();
        return computePrice();
    }

    @Override
    protected double computePrice() {
        Client client = getTerminalSender().getOwner();
        boolean areFriends =
            getTerminalSender().isFriend(getTerminalReceiver());
        client.getTariffPlan().setFriendship(areFriends);
        double price = client.computePrice(this);
        setPrice(price);
        getTerminalSender().updateBalance(price * -1);
        return price;
    }

}
