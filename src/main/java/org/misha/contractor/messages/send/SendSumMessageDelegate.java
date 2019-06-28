package org.misha.contractor.messages.send;

import org.misha.Message;
import org.misha.SendMessageDelegate;
import org.misha.contractor.Adder;
import org.misha.contractor.SumMessageContent;
import org.misha.contractor.messages.send.impl.SumMessageSender;
import org.misha.customer.messages.send.impl.TermsMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
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
        content.setLeft(100);
        content.setRight(200);
        content.setSum(adder.sum(100, 200).get());
        return msg;
    }
}
