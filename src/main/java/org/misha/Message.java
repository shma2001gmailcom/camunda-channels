package org.misha;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@ToString
@Slf4j
public class Message<T> {
    private String messageType;
    private String id = UUID.randomUUID().toString();
    private String traceId = UUID.randomUUID().toString();
    private String sender = "Ca-Ka Message Sender";
    private Date timestamp = new Date();
    private String correlationId;
    private T payload;

    public Message(T payload) {
        correlationId = UUID.randomUUID().toString();
        this.payload = payload;
    }
}