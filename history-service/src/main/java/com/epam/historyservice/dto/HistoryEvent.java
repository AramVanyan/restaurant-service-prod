package com.epam.historyservice.dto;

import com.epam.historyservice.event.EventType;
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
