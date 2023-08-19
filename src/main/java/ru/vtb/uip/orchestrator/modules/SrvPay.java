package ru.vtb.uip.orchestrator.modules;

import org.springframework.stereotype.Component;
import ru.vtb.uip.orchestrator.components.ProcessingResponse;
import ru.vtb.uip.orchestrator.components.SubFlow;

import javax.jms.Message;

@Component("SrvPay")
public class SrvPay implements SubFlow {
    @Override
    public ProcessingResponse process(Message message) {
        System.out.println("SrvPay: process");
        ProcessingResponse response = new ProcessingResponse();
        response.setNextStep("SrvFormatResponse");
        response.setSuccess(true);
        return response;
    }

    @Override
    public ProcessingResponse processRollback(Message message) {
        System.out.println("SrvPay: processRollback");
        return null;
    }
}
