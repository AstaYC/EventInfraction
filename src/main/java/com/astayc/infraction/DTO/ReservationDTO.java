package com.astayc.infraction.DTO;

import lombok.Data;

@Data
public class ReservationDTO {
    private Long eventId;
    private String email;

    public Long getEventId() {
        return eventId;
    }

    public String getEmail() {
        return email;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
