package com.epam.historyservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    private EventType eventType;
    private EventResult eventResult;
    private Long orderId;
}
