package org.misha.contractor.messages.send.impl;

import lombok.extern.slf4j.Slf4j;
import org.misha.Message;
import org.misha.Sender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Component
@EnableBinding(Source.class)
@Slf4j
public class SumMessageSender implements Sender {
    private final MessageChannel output;

    public SumMessageSender(@Qualifier("output") MessageChannel channel) {
        this.output = channel;
    }

    @Override
    public MessageChannel output() {
        return output;
    }

    @Override
    public void send(Message<?> m) {
        log.debug("\n\nSender: {};\nmessage: {}", this.getClass().getSimpleName(), m);
        supplyAsync(() -> {
            Sender.super.send(m);
            return null;
        });
    }
}