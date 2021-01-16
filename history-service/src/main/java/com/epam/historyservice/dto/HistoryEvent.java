package com.epam.historyservice.dto;

import com.epam.historyservice.event.EventType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryEvent {
    private EventType eventType;
    private boolean abort;
    private Object body;
}
