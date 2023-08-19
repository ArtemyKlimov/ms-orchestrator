package ru.vtb.uip.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import ru.vtb.uip.orchestrator.configuration.OrchestratorConfiguration;

import java.io.IOException;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJms
public class OrchestratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrchestratorApplication.class, args);
	}
}
