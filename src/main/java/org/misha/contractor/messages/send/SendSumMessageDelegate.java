package org.misha.contractor.messages.send;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.misha.Message;
import org.misha.SendMessageDelegate;
import org.misha.contractor.service.Adder;
import org.misha.contractor.data.SumMessageContent;
import org.misha.contractor.messages.send.impl.SumMessageSender;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class SendSumMessageDelegate extends SendMessageDelegate<SumMessageContent> {
    private final SumMessageSender messageSender;
    private final Adder adder;

    public SendSumMessageDelegate(final SumMessageSender messageSender, final Adder adder
    ) {
        super(messageSender);
        this.messageSender = messageSender;
        this.adder = adder;
    }

    @Override
    public Message<SumMessageContent> makeMessage() throws ExecutionException, InterruptedException {
        final Message<SumMessageContent> msg = new Message<>();
        msg.setMessageType("SumMessageContent");
        msg.setCorrelationId(UUID.randomUUID().toString());//initial request
        msg.setSender(messageSender.getClass().getSimpleName());
        SumMessageContent content = new SumMessageContent();
        content.setSum(adder.sum(100, 200).get());
        log.debug("\n---------------\nSender: {};\nmessage {} has been sent.\n", getClass().getSimpleName(), msg);
        return msg;
    }

    @Override
    public void execute(DelegateExecution context) throws Exception {
        log.debug("Sender: {}", messageSender.getClass().getSimpleName());
    }
}
