package com.astayc.infraction.Mapper;

import com.astayc.infraction.DTO.EventDTO;
import com.astayc.infraction.Entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventDTO eventToEventDTO(Event event);

    Event eventDTOToEvent(EventDTO eventDTO);
}