package org.misha.customer.messages.receive;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.misha.Message;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
@Slf4j
class ContractorsMessageListener implements JavaDelegate {

    @StreamListener(target = Sink.INPUT, condition = "(headers['messageType']?:'').startsWith('Sum')")
    public void messageReceived(String messageJson) throws Exception {
        log.debug("\n\n---------------\n\nReceiver: {};\n json received={}", getClass().getSimpleName(), messageJson);
        final TypeReference<Message<JsonNode>> typeRef = new TypeReference<Message<JsonNode>>() {};
        final Message<JsonNode> message = new ObjectMapper().readValue(messageJson, typeRef);
        log.debug("\n\n---------------\n\nReceiver: {};\nmessage: {};", this.getClass().getSimpleName(), message);
    }

    @Override
    public void execute(final DelegateExecution execution) throws Exception {
        //make it possible to start from ui
    }
}
