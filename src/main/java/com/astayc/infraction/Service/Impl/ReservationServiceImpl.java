package com.astayc.infraction.Service.Impl;

import com.astayc.infraction.DTO.ReservationDTO;
import com.astayc.infraction.Entity.Event;
import com.astayc.infraction.Entity.Reservation;
import com.astayc.infraction.Repository.EventRepository;
import com.astayc.infraction.Repository.ReservationRepository;
import com.astayc.infraction.Service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getCurrentCapacity() >= event.getMaxCapacity()) {
            throw new RuntimeException("Event is full");
        }

        boolean exists = reservationRepository
                .findByEventIdAndEmail(event.getId(), reservationDTO.getEmail())
                .isPresent();
        if (exists) {
            throw new RuntimeException("You are already registered for this event");
        }

        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservation.setEvent(event);

        event.setCurrentCapacity(event.getCurrentCapacity() + 1);

        reservationRepository.save(reservation);
        eventRepository.save(event);

        return modelMapper.map(reservation, ReservationDTO.class);
    }
}
