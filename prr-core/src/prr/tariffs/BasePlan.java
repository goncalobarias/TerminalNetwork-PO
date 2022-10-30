package prr.tariffs;

import java.io.Serial;

import prr.clients.ClientNormalLevel;
import prr.clients.ClientGoldLevel;
import prr.clients.ClientPlatinumLevel;
import prr.communications.TextCommunication;
import prr.communications.VoiceCommunication;
import prr.communications.VideoCommunication;

public class BasePlan extends TariffPlan {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210192349L;

    public double computePrice(ClientNormalLevel level,
      TextCommunication communication) {
        double price = 0.0;
        int units = communication.getUnits();

        if (units < 50) {
            price += 10.0;
        } else if (units < 100) {
            price += 16.0;
        } else {
            price += units * 2;
        }

        return price;
    }

    public double computePrice(ClientNormalLevel level,
      VoiceCommunication communication) {
        return 20.0;
    }

    public double computePrice(ClientNormalLevel level,
      VideoCommunication communication) {
        return 30.0;
    }

    public double computePrice(ClientGoldLevel level, TextCommunication communication) {
        double price = 0.0;
        int units = communication.getUnits();

        if (units < 50) {
            price += 10.0;
        } else if (units < 100) {
            price += 10.0;
        } else {
            price += units * 2;
        }

        return price;
    }

    public double computePrice(ClientGoldLevel level,
      VoiceCommunication communication) {
        return 10.0;
    }

    public double computePrice(ClientGoldLevel level,
      VideoCommunication communication) {
        return 20.0;
    }

    public double computePrice(ClientPlatinumLevel level, TextCommunication communication) {
        double price = 0.0;
        int units = communication.getUnits();

        if (units < 50) {
            price += 0.0;
        } else if (units < 100) {
            price += 4.0;
        } else {
            price += 4.0;
        }

        return price;
    }

    public double computePrice(ClientPlatinumLevel level,
      VoiceCommunication communication) {
        return 10.0;
    }

    public double computePrice(ClientPlatinumLevel level,
      VideoCommunication communication) {
        return 10.0;
    }

}
