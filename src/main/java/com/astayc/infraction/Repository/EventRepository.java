package com.astayc.infraction.Repository;

import com.astayc.infraction.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}