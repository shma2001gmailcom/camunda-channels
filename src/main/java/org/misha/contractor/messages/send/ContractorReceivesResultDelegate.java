package org.misha.contractor.messages.send;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ContractorReceivesResultDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        RuntimeService runtimeService = ProcessEngines.getDefaultProcessEngine().getRuntimeService();
        String processId = execution.getProcessInstance().getId();
        Map<String, Object> globalVars = (Map<String, Object>) runtimeService.getVariable(processId, "globalVars");
        globalVars.put("msg", globalVars.get("msg") + "Changed_by_" + getClass().getSimpleName());
        log.debug("\n\n\n\n {}", runtimeService.getVariable(processId, "globalVars"));
    }
}
