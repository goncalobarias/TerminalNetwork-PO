package prr.clients;

import java.io.Serial;

import prr.communications.TextCommunication;
import prr.communications.VoiceCommunication;
import prr.communications.VideoCommunication;

public class ClientGoldLevel extends Client.Level {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210190314L;

    public ClientGoldLevel(Client client, double payments, double debts,
      int numberOfConsecutiveTextCommunications,
      int numberOfConsecutiveVideoCommunications) {
        client.super(payments, debts, numberOfConsecutiveTextCommunications,
                    numberOfConsecutiveVideoCommunications);
    }

    @Override
    protected String getLevelType() {
        return "GOLD";
    }

    public double computePrice(TextCommunication communication) {
        return getTariffPlan().computePrice(this, communication);
    }

    public double computePrice(VoiceCommunication communication) {
        return getTariffPlan().computePrice(this, communication);
    }

    public double computePrice(VideoCommunication communication) {
        return getTariffPlan().computePrice(this, communication);
    }

    @Override
    protected void verifyLevelUpdateConditions() {
        if (getBalance() < 0.0) {
            updateLevel(new ClientNormalLevel(getClient(), getPayments(),
                getDebts(), getNumberOfConsecutiveTextCommunications(),
                getNumberOfConsecutiveVideoCommunications()));
        } else if (getNumberOfConsecutiveVideoCommunications() == 5) {
            updateLevel(new ClientPlatinumLevel(getClient(), getPayments(),
                getDebts(), getNumberOfConsecutiveTextCommunications(),
                getNumberOfConsecutiveVideoCommunications()));
        }
    }

}
