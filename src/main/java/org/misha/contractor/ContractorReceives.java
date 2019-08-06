package org.misha.contractor;

import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ContractorReceives implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(ContractorReceives.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        RuntimeService runtimeService = ProcessEngines.getDefaultProcessEngine().getRuntimeService();
        String processId = execution.getProcessInstance().getId();
        Map<String, Object> globalVars = (Map<String, Object>) runtimeService.getVariable(processId, "globalVars");
        globalVars.put("msg", globalVars.get("msg") + "Changed_by_" + getClass().getSimpleName());
        log.debug("\n\n\n\n {}", runtimeService.getVariable(processId, "globalVars"));
    }
}
