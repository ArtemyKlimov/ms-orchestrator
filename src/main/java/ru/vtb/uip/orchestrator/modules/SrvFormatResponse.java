package ru.vtb.uip.orchestrator.modules;

import org.springframework.stereotype.Component;
import ru.vtb.uip.orchestrator.components.ProcessingResponse;
import ru.vtb.uip.orchestrator.components.SubFlow;

import javax.jms.Message;

@Component("SrvFormatResponse")
public class SrvFormatResponse implements SubFlow {
    @Override
    public ProcessingResponse process(Message message) {
        System.out.println("SrvFormatResponse: process");
        ProcessingResponse response = new ProcessingResponse();
        response.setResponseText("<Text>This is final text response</Text>");
        response.setSuccess(true);
        return response;
    }

    @Override
    public ProcessingResponse processRollback(Message message) {
        System.out.println("SrvFormatResponse: processRollback");
        return null;
    }
}
