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
    public double stopCommunication(int duration) {
        setProgress(false);
        getTerminalReceiver().setOngoingCommunication(null);
        getTerminalReceiver().unBusy();
        getTerminalSender().setOngoingCommunication(null);
        getTerminalSender().unBusy();
        // TODO: fix this horrible implementation (VERY IMPORTANT)
        getTerminalSender().getOwner()
            .increaseNumberOfConsecutiveVideoCommunications();
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
