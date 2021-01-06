package com.epam.historyservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompensationEvent {
    EventState eventState;
    EventType eventType;
    Long orderId;
}
