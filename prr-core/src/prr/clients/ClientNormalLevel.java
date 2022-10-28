package prr.clients;

import java.io.Serial;

public class ClientNormalLevel extends Client.Level {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192342L;

    public ClientNormalLevel(Client client, double payments, double debts,
      int numberOfConsecutiveTextCommunications,
      int numberOfConsecutiveVideoCommunications) {
        client.super(payments, debts, numberOfConsecutiveTextCommunications,
                    numberOfConsecutiveVideoCommunications);
    }

    @Override
    protected String getLevelType() {
        return "NORMAL";
    }

    @Override
    protected void verifyLevelUpdateConditions() {
        if (getBalance() > 500.0) {
            updateLevel(new ClientGoldLevel(getClient(), getPayments(),
                getDebts(), getNumberOfConsecutiveTextCommunications(),
                getNumberOfConsecutiveVideoCommunications()));
        }
    }

}
