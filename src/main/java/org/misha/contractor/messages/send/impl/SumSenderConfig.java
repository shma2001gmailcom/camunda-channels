package org.misha.contractor.messages.send.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;

@Configuration
class SumSenderConfig {

    @Bean
    SumMessageSender sumMessageSender(@Qualifier("output") MessageChannel output) {
        return new SumMessageSender(output);
    }
}