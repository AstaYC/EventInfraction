package com.astayc.infraction.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private int maxCapacity;
    private int currentCapacity;
}