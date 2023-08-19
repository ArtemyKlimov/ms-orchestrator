package ru.vtb.uip.orchestrator.components;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ProcessingResponse {
    private String nextStep;
    private String responseText;
    private boolean isSuccess;
    private Map<String, String> properties;
}
