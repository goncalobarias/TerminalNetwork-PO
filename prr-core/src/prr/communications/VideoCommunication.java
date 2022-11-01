package prr.communications;

import java.io.Serial;

import prr.clients.Client;
import prr.terminals.Terminal;

public class VideoCommunication extends InteractiveCommunication {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192345L;

    public VideoCommunication(int id, Terminal terminalReceiver,
      Terminal terminalSender) {
        super(id, terminalReceiver, terminalSender);
    }

    @Override
    public String getCommunicationType() {
        return "VIDEO";
    }

    @Override
    public double finishCommunication(int duration) {
        finishInteractiveCommunication(duration);
        getTerminalSender().getOwner()
            .increaseNumberOfConsecutiveVideoCommunications();
        return computePrice();
    }

    @Override
    protected double computePrice() {
        Client client = getTerminalSender().getOwner();
        boolean areFriends =
            getTerminalSender().isFriend(getTerminalReceiver());
        double price = client.getLevel().computePrice(this);
        client.getTariffPlan().setFriendship(areFriends);
        setPrice(price);
        getTerminalSender().updateBalance(price * -1);
        return price;
    }

}
