package ru.vtb.uip.orchestrator.configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "srvcheck")
@Getter
@Setter
public class SrvCheckConfiguration {

    @Value("${srvCheck.param1}")
    public String param1;
    @Value("${srvCheck.param2}")
    public String param2;
    @Value("${orchestrator.artemis.user}")
    public String user;

}
