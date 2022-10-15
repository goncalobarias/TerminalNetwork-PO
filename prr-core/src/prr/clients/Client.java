package prr.clients;

import java.io.Serializable;

public class Client implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202210150050L;

    private String _id;
    private String _name;
    private int _taxId;
    private Level _level;

    public Client(String id, String name, int taxId) {
        _id = id;
        _name = name;
        _taxId = taxId;
        _level = new ClientNormalLevel(this);
    }

    public class DefaultDeliveryMethod extends NotificationDeliveryMethod {

        /** Serial number for serialization. */
        private static final long serialVersionUID = 202210150051L;

    }

    public abstract class Level implements Serializable {

        /** Serial number for serialization. */
        private static final long serialVersionUID = 202210150052L;

    }
}
