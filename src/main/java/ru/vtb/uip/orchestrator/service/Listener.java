package ru.vtb.uip.orchestrator.service;


import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import ru.vtb.uip.orchestrator.components.ProcessingResponse;
import ru.vtb.uip.orchestrator.components.SubFlow;
import ru.vtb.uip.orchestrator.configuration.OrchestratorConfiguration;
import ru.vtb.uip.orchestrator.configuration.SrvCheckConfiguration;
import ru.vtb.uip.orchestrator.utils.MessageCopier;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;


@Service
public class Listener {

    @Autowired
    ApplicationContext context;
    @Autowired
    Sender sender;
    @Autowired
    private OrchestratorConfiguration configuration;

    private final String SERVICENAME = "Servicename";

    private final String queue = "${orchestrator.artemis.inputQueue}";
    @JmsListener(destination = queue, containerFactory = "listenerServiceFactory")
    public void onMessage(Message message, Session session) throws JMSException {

        String serviceName = message.getStringProperty(SERVICENAME);
        OrchestratorConfiguration.Step currentStep = getCurrentStep(serviceName);
        Object srvCheck = context.getBean(currentStep.getBeanName());

        SubFlow s = (SubFlow) srvCheck;
        //Message m.b. TextMessage, ByteMessage MapMessage etc.
        ProcessingResponse response = s.process(message);
        /*  или так:
            Class<?> act = Class.forName(className);
            //SubFlow s = (SubFlow) act.newInstance(); // deprecated
            s.process(message);
         */
        /* или так:
            Method processMethod = cls.getClass().getMethod("process", Message.class);
            processMethod.invoke(cls, message);
         */
        System.out.println("JMSType = " + message.getJMSType());
        try {
            //TODO save information into Database
            if (!Strings.isEmpty(response.getNextStep())) {
                String destination = configuration.getArtemis().getInputQueue();
                sender.Send(createOutputMessage(currentStep, message, response, session), destination);
            } else {
                String destination = configuration.getArtemis().getOutputQueue();
                sender.Send(createOutputMessage(currentStep, message, response, session), destination);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            s.processRollback(message);
        }

    }

    private Message createOutputMessage(OrchestratorConfiguration.Step currentStep, Message input, ProcessingResponse response, Session session) throws JMSException {
        Message m = MessageCopier.copyMessage(input, session);
        if (!Strings.isEmpty(response.getResponseText())) {
            m.setStringProperty(currentStep.getName(), response.getResponseText());
        }
        if (response.getNextStep() != null) {
            m.setStringProperty(SERVICENAME, response.getNextStep());
        }
        if (response.getProperties() != null) {
            for (var entry : response.getProperties().entrySet()) {
                m.setStringProperty(entry.getKey(), entry.getValue());
            }
        }
        return m;
    }

    private OrchestratorConfiguration.Step getCurrentStep(String serviceName) {
        OrchestratorConfiguration.Step currentStep;
        if (!Strings.isEmpty(serviceName)) {
            currentStep = configuration.getStepByName(serviceName);
        } else {
            currentStep = configuration.getEntrypoint();
        }
        return currentStep;
    }
}
