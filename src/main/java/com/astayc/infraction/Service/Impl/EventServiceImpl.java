package com.astayc.infraction.Service.Impl;

import com.astayc.infraction.DTO.EventDTO;
import com.astayc.infraction.Entity.Event;
import com.astayc.infraction.Mapper.EventMapper;
import com.astayc.infraction.Repository.EventRepository;
import com.astayc.infraction.Service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper = EventMapper.INSTANCE;

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = eventMapper.eventDTOToEvent(eventDTO);
        event.setCurrentCapacity(0); // Initialize capacity
        return eventMapper.eventToEventDTO(eventRepository.save(event));
    }

    @Override
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found")); // Custom exception recommended
        return eventMapper.eventToEventDTO(event);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::eventToEventDTO)
                .collect(Collectors.toList());
    }
}