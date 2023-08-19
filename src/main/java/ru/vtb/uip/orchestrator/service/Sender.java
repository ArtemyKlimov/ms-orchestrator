package ru.vtb.uip.orchestrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import ru.vtb.uip.orchestrator.configuration.OrchestratorConfiguration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class Sender {
    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    OrchestratorConfiguration configuration;

    public void Send(Message inputMessage, String destination) throws Exception{
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return inputMessage;
            }
        });
    }
}
