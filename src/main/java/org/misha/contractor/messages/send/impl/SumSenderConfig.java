package org.misha.contractor.messages.send.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.MessageChannel;

@Configuration
class SumSenderConfig {
    @Autowired
    private MessageChannel output;

    @Bean
    SumMessageSender sumMessageSender() {
        return new SumMessageSender(output());
    }

    @Bean
    @Qualifier("output")
    @Primary
    MessageChannel output() {
        return output;
    }
}