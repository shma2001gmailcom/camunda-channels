package org.misha.customer.messages.send;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.misha.SendMessageDelegate;
import org.misha.customer.data.TermsMessageContent;
import org.misha.Message;
import org.misha.customer.messages.send.impl.TermsMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class ConsumerSendsTermsMessageDelegate extends SendMessageDelegate<TermsMessageContent> {
    private final TermsMessageSender messageSender;

    @Autowired
    public ConsumerSendsTermsMessageDelegate(TermsMessageSender messageSender) {
        super(messageSender);
        this.messageSender = messageSender;
    }

    @Override
    public Message<TermsMessageContent> makeMessage(DelegateExecution context) {
        final Message<TermsMessageContent> msg = new Message<>();
        msg.setMessageType("TermsMessageContent");
        msg.setCorrelationId(UUID.randomUUID().toString());//initial request
        msg.setSender(messageSender.getClass().getSimpleName());
        final TermsMessageContent content = new TermsMessageContent();
        content.setId(UUID.randomUUID().toString());
        content.setLeft(1);
        content.setRight(2);
        msg.setPayload(content);
        RuntimeService runtimeService = ProcessEngines.getDefaultProcessEngine().getRuntimeService();
        Map<String, Object> globalVars = new HashMap<>();
        String id = context.getProcessInstance().getId();
        runtimeService.setVariable(id, "globalVars", globalVars);
        globalVars.put("msg", msg);
        globalVars.put("procId", id);
        log.debug("\n\n\n\n {}",runtimeService.getVariable(id, "globalVars"));
        return msg;
    }


}
