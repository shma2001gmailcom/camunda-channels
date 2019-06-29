package org.misha.contractor.messages.receive;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.misha.Message;
import org.misha.contractor.service.Adder;
import org.misha.contractor.data.SumMessageContent;
import org.misha.contractor.messages.send.impl.SumMessageSender;
import org.misha.customer.data.TermsMessageContent;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
@EnableBinding(Sink.class)
@Slf4j
class CustomersMessageListener implements JavaDelegate {
    private final Adder adder;
    private final SumMessageSender sumMessageSender;

    CustomersMessageListener(Adder adder, SumMessageSender sumMessageSender) {
        this.adder = adder;
        this.sumMessageSender = sumMessageSender;
    }

    @StreamListener(target = Sink.INPUT, condition = "(headers['messageType']?:'').startsWith('Terms')")
    public void messageReceived(String messageJson) throws IOException, ExecutionException, InterruptedException {
        log.debug("\n\n---------------\n\nReceiver: json received={}", messageJson);
        final TypeReference<Message<TermsMessageContent>> typeRef = new TypeReference<Message<TermsMessageContent>>() {};
        final Message<TermsMessageContent> message = new ObjectMapper().readValue(messageJson, typeRef);
        final int left = message.getPayload().getLeft();
        final int right = message.getPayload().getRight();
        log.debug(
                "\n\n---------------\n\nReceiver: {};\nmessageType: {};\ntraceId: {};\nleft term:  {};\nright term: " + "{};",
                this.getClass().getSimpleName(),
                message.getMessageType(),
                message.getTraceId(),
                left,
                right);
        final Message<SumMessageContent> msg = makeReplyMessage(message, adder.sum(left, right));
        sumMessageSender.send(msg);
    }

    private Message<SumMessageContent> makeReplyMessage(Message<TermsMessageContent> message,
                                                        Future<Integer> calculationPlan
    ) throws ExecutionException, InterruptedException {
        final Message<SumMessageContent> msg = new Message<>();
        msg.setMessageType("SumMessageContent");
        msg.setCorrelationId(message.getCorrelationId());//populate correlation for call chain
        final SumMessageContent content = new SumMessageContent();
        final Integer result = calculationPlan.get();
        content.setSum(result);
        content.setLeft(message.getPayload().getLeft());
        content.setRight(message.getPayload().getRight());
        msg.setPayload(content);
        return msg;
    }

    @Override
    public void execute(final DelegateExecution execution) throws Exception {
        log.debug("CustomersMessageListener");
    }
}

