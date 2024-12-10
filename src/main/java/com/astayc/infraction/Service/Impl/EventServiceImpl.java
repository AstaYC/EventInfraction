package com.astayc.infraction.Service.Impl;

import com.astayc.infraction.DTO.EventDTO;
import com.astayc.infraction.Entity.Event;
import com.astayc.infraction.Repository.EventRepository;
import com.astayc.infraction.Service.EventService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    public EventServiceImpl( EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        // Map DTO to Event entity
        Event event = modelMapper.map(eventDTO, Event.class);
        event.setCurrentCapacity(0);

        // Save and map back to DTO
        return modelMapper.map(eventRepository.save(event), EventDTO.class);
    }

    @Override
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Map to DTO
        return modelMapper.map(event, EventDTO.class);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        // Map each Event to EventDTO
        return eventRepository.findAll()
                .stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }
}
