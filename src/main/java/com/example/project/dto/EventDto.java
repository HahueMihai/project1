package com.example.project.dto;

import com.example.project.model.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {

    private String title;

    private String description;

    private Instant startTime;

    private Instant endTime;

}
