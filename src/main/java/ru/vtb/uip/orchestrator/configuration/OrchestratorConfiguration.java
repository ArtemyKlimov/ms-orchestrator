package ru.vtb.uip.orchestrator.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@ConfigurationProperties(prefix = "orchestrator")
@Getter
@Setter
public class OrchestratorConfiguration {

    private List<Step> steps;
    private Artemis artemis;
    @Getter
    @Setter
    public static class Artemis {
        private String url;
        private String clientId;
        private String user;
        private String password;
        private String inputQueue;
        private String outputQueue;
        private String errorQueue;
    }
    @Getter
    @Setter
    public static class Step {

        private String name;
        private String success;
        private String failure;
        private String className;
        private String beanName;
        @Nullable
        private boolean entrypoint;
    }

    public Step getEntrypoint() {
        return steps.stream()
                .filter(Step::isEntrypoint)
                .findFirst()
                .orElse(null);
    }

    public Step getStepByName(String step) {
        return steps.stream()
                .filter(s -> step.equals(s.getName()))
                .findFirst()
                .orElse(null);
    }
}
