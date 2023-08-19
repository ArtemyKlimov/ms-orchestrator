package ru.vtb.uip.orchestrator.configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@Configuration
public class ListenerConnectionConfiguration {

    @Autowired
    OrchestratorConfiguration configuration;
    @Bean
    public JmsListenerContainerFactory<?> listenerServiceFactory(@Qualifier("artemisJMSConnectionFactory")ConnectionFactory service) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setClientId(configuration.getArtemis().getClientId());
        factory.setConnectionFactory(service);
        //factory.setPubSubDomain(false);
        factory.setSessionTransacted(true);
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        factory.setReceiveTimeout(100000L);

        return  factory;
    }
}
