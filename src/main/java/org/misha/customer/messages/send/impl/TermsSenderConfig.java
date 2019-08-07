package org.misha.customer.messages.send.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;

@Configuration
class TermsSenderConfig {
    @Bean
    TermsMessageSender messageSender(@Qualifier("output") MessageChannel output) {
        return new TermsMessageSender(output);
    }
}