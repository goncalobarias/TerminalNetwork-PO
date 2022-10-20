package prr.clients;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.Serial;

import prr.util.Visitable;
import prr.util.Visitor;
import prr.notifications.NotificationDeliveryMethod;
import prr.notifications.Notification;
import prr.tariffs.BasePlan;
import prr.tariffs.TariffPlan;
import prr.terminals.Terminal;

public class Client implements Serializable, Visitable {

    /** Serial number for serialization. */
    @Serial
    private static final long serialVersionUID = 202210150050L;

    private final String _id;
    private String _name;
    private int _taxId;
    private boolean _receiveNotifications;
    private NotificationDeliveryMethod _deliveryMethod;
    private Queue<Notification> _notifications;
    private List<Terminal> _terminals;
    private Level _level;

    public Client(String id, String name, int taxId) {
        _id = id;
        _name = name;
        _taxId = taxId;
        _receiveNotifications = true;
        _deliveryMethod = new DefaultDeliveryMethod();
        _notifications = new LinkedList<Notification>();
        _terminals = new ArrayList<Terminal>();
        _level = new ClientNormalLevel(this);
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public int getTaxId() {
        return _taxId;
    }

    public int getNumberOfTerminals() {
        return _terminals.size();
    }

    public double getPayments() {
        return _level.getPayments();
    }

    public double getDebts() {
        return _level.getDebts();
    }

    public String getLevelType() {
        return _level.getLevelType();
    }

    public boolean hasNotificationsEnabled() {
        return _receiveNotifications;
    }

    public void addTerminal(Terminal terminal) {
        _terminals.add(terminal);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public class DefaultDeliveryMethod extends NotificationDeliveryMethod {

        /** Serial number for serialization. */
        @Serial
        private static final long serialVersionUID = 202210192341L;

        public void deliver(Notification notification) {
        }

    }

    public abstract class Level implements Serializable {

        /** Serial number for serialization. */
        @Serial
        private static final long serialVersionUID = 202210150052L;

        private double _payments;
        private double _debts;
        private TariffPlan _plan;

        public Level() {
            _payments = 0.0;
            _debts = 0.0;
            _plan = new BasePlan();
        }

        public abstract String getLevelType();

        public double getPayments() {
            return _payments;
        }

        public double getDebts() {
            return _debts;
        }

    }

}
