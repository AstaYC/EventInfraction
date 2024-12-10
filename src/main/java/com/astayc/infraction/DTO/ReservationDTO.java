package com.astayc.infraction.DTO;

import lombok.Data;

@Data
public class ReservationDTO {
    private Long eventId; // Event the reservation is for
    private String email; // Customer's email
}