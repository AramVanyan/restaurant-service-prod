package com.epam.kitchenservice.dto;

import com.epam.kitchenservice.event.EventType;
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
    private Object body;
}
