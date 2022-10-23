package prr.util;

import prr.communications.Communication;

public interface CommunicationVisitor<T> {

    T visit(Communication communication);

}
