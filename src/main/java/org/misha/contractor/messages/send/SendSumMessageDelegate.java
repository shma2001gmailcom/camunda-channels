package org.misha.contractor.messages.send;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.misha.Message;
import org.misha.SendMessageDelegate;
import org.misha.contractor.Adder;
import org.misha.contractor.SumMessageContent;
import org.misha.contractor.messages.send.impl.SumMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class SendSumMessageDelegate  extends SendMessageDelegate<SumMessageContent> {
    @Autowired
    SumMessageSender messageSender;
    @Autowired
    Adder adder;

    public SendSumMessageDelegate(SumMessageSender messageSender) {
        super(messageSender);
    }

    @Override
    public Message<SumMessageContent> makeMessage() throws ExecutionException, InterruptedException {
        final Message<SumMessageContent> msg = new Message<>();
        msg.setMessageType("SumMessageContent");
        msg.setCorrelationId(UUID.randomUUID().toString());//initial request
        msg.setSender(messageSender.getClass().getSimpleName());
        SumMessageContent content = new SumMessageContent();
        content.setSum(adder.sum(100, 200).get());
        log.debug("\n---------------\nSender: {};\nmessage {} has been sent.\n","SendSumMessageDelegate", msg);
        return msg;
    }

    @Override
    public void execute(DelegateExecution context) throws Exception {
        log.debug("Sender: {}", messageSender.getClass().getSimpleName());
    }
}
