package com.astayc.infraction.Repository;

import com.astayc.infraction.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByEventIdAndEmail(Long eventId, String email);
}