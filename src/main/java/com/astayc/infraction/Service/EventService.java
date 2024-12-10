package com.astayc.infraction.Service;

import com.astayc.infraction.DTO.EventDTO;

import java.util.List;

public interface EventService {
    EventDTO createEvent(EventDTO eventDTO);

    EventDTO getEventById(Long id);

    List<EventDTO> getAllEvents();
}