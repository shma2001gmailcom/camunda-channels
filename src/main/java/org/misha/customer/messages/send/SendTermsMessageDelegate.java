package org.misha.customer.messages.send;

import lombok.extern.slf4j.Slf4j;
import org.misha.SendMessageDelegate;
import org.misha.customer.TermsMessageContent;
import org.misha.Message;
import org.misha.customer.messages.send.impl.TermsMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class SendTermsMessageDelegate extends SendMessageDelegate<TermsMessageContent> {
    @Autowired
    TermsMessageSender messageSender;

    public SendTermsMessageDelegate(TermsMessageSender messageSender) {
        super(messageSender);
    }

    @Override
    public Message<TermsMessageContent> makeMessage() {
        final Message<TermsMessageContent> msg = new Message<>();
        msg.setMessageType("TermsMessageContent");
        msg.setCorrelationId(UUID.randomUUID().toString());//initial request
        msg.setSender(messageSender.getClass().getSimpleName());
        final TermsMessageContent content = new TermsMessageContent();
        content.setId(UUID.randomUUID().toString());
        content.setLeft(1);
        content.setRight(2);
        msg.setPayload(content);
        return msg;
    }
}
