package ru.vtb.uip.orchestrator.components;

import javax.jms.Message;

public interface SubFlow {
    ProcessingResponse process(Message message);
    ProcessingResponse processRollback(Message message);
}
