package ru.vtb.uip.orchestrator.modules;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.vtb.uip.orchestrator.components.ProcessingResponse;
import ru.vtb.uip.orchestrator.components.SubFlow;
import ru.vtb.uip.orchestrator.configuration.SrvCheckConfiguration;

import javax.jms.Message;

//@Service
@Component("SrvCheck") //надо указать название бина, иначе возьмется значение имени класса, но с прописной буквы
public class SrvCheck implements SubFlow {
    @Value("${srvCheck.param1}")
    String param1;
    @Value("${srvCheck.param2}")
    String param2;
    @Value("${srvCheck.url}")
    String url;

    @Override
    public ProcessingResponse process(Message message) {
        //TODO smth e.g. http call
        System.out.println("SrvCheck: process");
        System.out.println("Param1 = " + param1 + "; pram2 = " + param2 + " url = " + url);
        ProcessingResponse response = new ProcessingResponse();
        response.setResponseText("ResponseText");
        response.setNextStep("SrvPay");
        response.setSuccess(true);
        return response;
    }

    @Override
    public ProcessingResponse processRollback(Message message) {
        //TODO smth e.g. http call (compensatory action)
        System.out.println("SrvCheck: processRollback");
        return  null;
    }
}
