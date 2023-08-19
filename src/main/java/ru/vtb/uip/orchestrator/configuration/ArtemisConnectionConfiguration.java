package ru.vtb.uip.orchestrator.configuration;


import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@Configuration
public class ArtemisConnectionConfiguration {

    @Autowired
    OrchestratorConfiguration configuration;

    @Bean
    public ConnectionFactory artemisJMSConnectionFactory() throws JMSException {
        return new ActiveMQConnectionFactory(   configuration.getArtemis().getUrl(),
                                                configuration.getArtemis().getUser(),
                                                configuration.getArtemis().getPassword());
    }


    @Bean
    public JmsTemplate jmsTemplate(@Qualifier("artemisJMSConnectionFactory") ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setMessageIdEnabled(true);
        jmsTemplate.setMessageTimestampEnabled(true);
        jmsTemplate.setExplicitQosEnabled(true);
        return jmsTemplate;
    }
}
