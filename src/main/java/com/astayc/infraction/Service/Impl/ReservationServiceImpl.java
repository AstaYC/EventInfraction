package com.astayc.infraction.Service.Impl;

import com.astayc.infraction.DTO.ReservationDTO;
import com.astayc.infraction.Entity.Event;
import com.astayc.infraction.Entity.Reservation;
import com.astayc.infraction.Exception.ResourceNotFoundException;
import com.astayc.infraction.Repository.EventRepository;
import com.astayc.infraction.Repository.ReservationRepository;
import com.astayc.infraction.Service.ReservationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service

public class ReservationServiceImpl implements ReservationService {

    private final EventRepository eventRepository;
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;

    public ReservationServiceImpl(EventRepository eventRepository,
                                  ReservationRepository reservationRepository,
                                  ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Event event = eventRepository.findById(reservationDTO.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + reservationDTO.getEventId()));

        if(event.getCurrentCapacity() >= event.getMaxCapacity()){
            throw new IllegalArgumentException("the event is full up");
        }

        boolean exist2 = reservationRepository.findByEventIdAndEmail(event.getId(), reservationDTO.getEmail()).isPresent();
        if (exist2){
            throw new IllegalArgumentException("the reservation already exists");
        }

        LocalDate date = LocalDate.now();

        if(event.getDate().isAfter(date)){
            throw new IllegalArgumentException("the time is over for this event");
        }

        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservation.setEvent(event);

        event.setCurrentCapacity(event.getCurrentCapacity() + 1);

        reservationRepository.save(reservation);
        eventRepository.save(event);

        return modelMapper.map(reservation, ReservationDTO.class);
    }


}
