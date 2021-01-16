package com.epam.paymentservice.dto;

import com.epam.paymentservice.event.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEvent {
    private EventType eventType;
    private boolean abort;
    private Object body;
}
