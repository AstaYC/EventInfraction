package com.astayc.infraction.Service.Impl;

import com.astayc.infraction.DTO.ReservationDTO;
import com.astayc.infraction.Entity.Event;
import com.astayc.infraction.Entity.Reservation;
import com.astayc.infraction.Mapper.EventMapper;
import com.astayc.infraction.Repository.EventRepository;
import com.astayc.infraction.Repository.ReservationRepository;
import com.astayc.infraction.Service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private  EventRepository eventRepository;

    @Autowired
    private  ReservationRepository reservationRepository;

    private final EventMapper eventMapper = EventMapper.INSTANCE;

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        // Fetch the event
        Event event = eventRepository.findById(reservationDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Check capacity
        if (event.getCurrentCapacity() >= event.getMaxCapacity()) {
            throw new RuntimeException("Event is full");
        }

        // Check duplicate reservation
        boolean exists = reservationRepository
                .findByEventIdAndEmail(event.getId(), reservationDTO.getEmail())
                .isPresent();
        if (exists) {
            throw new RuntimeException("You are already registered for this event");
        }

        // Create reservation
        Reservation reservation = eventMapper.reservationDTOToReservation(reservationDTO);
        reservation.setEvent(event);
        event.setCurrentCapacity(event.getCurrentCapacity() + 1);

        // Save reservation and update event
        reservationRepository.save(reservation);
        eventRepository.save(event);

        return eventMapper.reservationToReservationDTO(reservation);
    }
}