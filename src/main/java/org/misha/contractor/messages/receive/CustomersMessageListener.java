package org.misha.contractor.messages.receive;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.misha.Message;
import org.misha.customer.TermsMessageContent;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
@Slf4j
class CustomersMessageListener implements JavaDelegate {
    @StreamListener(target = Sink.INPUT, condition = "(headers['messageType']?:'').startsWith('Terms')")
    public void messageReceived(String messageJson) throws Exception {
        log.debug("\n\n---------------\n\nReceiver: json received={}", messageJson);
        final TypeReference<Message<TermsMessageContent>> typeRef =
                new TypeReference<Message<TermsMessageContent>>() {};
        final Message<TermsMessageContent> message = new ObjectMapper().readValue(messageJson, typeRef);
        log.debug("\n\n---------------\n\nReceiver={}: {}messageType={}; \ntraceId={}; \nleft term: {}; \nright term:{}",
                  this.getClass().getSimpleName(), message.getMessageType(), message.getTraceId(), message.getPayload().getLeft(),
                  message.getPayload().getRight());
    }

    @Override
    public void execute(final DelegateExecution execution) throws Exception {
        log.debug("CustomersMessageListener");
    }
}

