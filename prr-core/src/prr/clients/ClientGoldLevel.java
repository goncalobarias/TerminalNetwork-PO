package prr.clients;

import java.io.Serial;

import prr.communications.TextCommunication;
import prr.communications.VideoCommunication;
import prr.communications.VoiceCommunication;
import prr.tariffs.TariffPlan;

public class ClientGoldLevel extends Client.Level {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210190314L;

    public ClientGoldLevel(Client client, double payments, double debts,
      TariffPlan plan) {
        client.super(payments, debts, plan);
    }

    @Override
    protected String getLevelType() {
        return "GOLD";
    }

    @Override
    public double computePrice(TextCommunication communication) {
        return getTariffPlan().computePrice(this, communication);
    }

    @Override
    public double computePrice(VoiceCommunication communication) {
        return getTariffPlan().computePrice(this, communication);
    }

    @Override
    public double computePrice(VideoCommunication communication) {
        return getTariffPlan().computePrice(this, communication);
    }

    @Override
    protected void verifyLevelUpdateConditions(boolean hasPayed) {
        if (hasPayed) {
            return;
        }
        if (getBalance() < 0D) {
            updateLevel(new ClientNormalLevel(getClient(), getPayments(),
                getDebts(), getTariffPlan()));
        } else if (getNumberOfConsecutiveVideoCommunications() == 5) {
            updateLevel(new ClientPlatinumLevel(getClient(), getPayments(),
                getDebts(), getTariffPlan()));
        }
    }

}
