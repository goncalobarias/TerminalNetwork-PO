package prr.app.visitors;

import prr.util.CommunicationVisitor;
import prr.communications.Communication;

public class RenderCommunication implements CommunicationVisitor<String> {

    @Override
    public String visit(Communication communication) {
        return communication.getCommunicationType() + "|" +
                communication.getId() + "|" +
                communication.getSenderId() + "|" +
                communication.getReceiverId() + "|" +
                Math.round(communication.getUnits()) + "|" +
                Math.round(communication.getPrice()) + "|" +
                (communication.isOngoing() ? "ONGOING" : "FINISHED");
    }

}
