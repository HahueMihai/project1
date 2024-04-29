package com.example.project.repository;

import com.example.project.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
    Optional<Event> findByTitle(String title);

    Page<Event> findAll(Pageable pageable);

    Page<Event> findByStartTimeBetween(Instant startTime, Instant startTime1, Pageable pageable);
}
