package prr.util;

import prr.communications.Communication;

public interface CommunicationVisitor {

    String visit(Communication communication);

}
