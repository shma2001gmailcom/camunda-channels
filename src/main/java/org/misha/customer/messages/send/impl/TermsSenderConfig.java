package org.misha.customer.messages.send.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.MessageChannel;

@Configuration
class TermsSenderConfig {
    @Autowired
    private MessageChannel output;//1

    @Bean
    TermsMessageSender messageSender() {
        return new TermsMessageSender(output());
    }

    @Bean
    @Qualifier("output")
    @Primary
    MessageChannel output() {
        return output;
    }
}