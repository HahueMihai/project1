package com.example.project.service;

import com.example.project.dto.EventDto;
import com.example.project.model.Event;
import com.example.project.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;


    private EventService eventService;

    @BeforeEach
    void setUp() {
        eventService = new EventService(eventRepository);

    }

    @Test
    void createEvent_Success() {
        EventDto eventDto = new EventDto("Test Event", "Description", Instant.now(), Instant.now());
        Event savedEvent = new Event(1, "Test Event", "Description", Instant.now(), Instant.now(),null);

        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        Event createdEvent = eventService.createEvent(eventDto);

        verify(eventRepository, times(1)).save(any(Event.class));
        assertEquals(savedEvent, createdEvent);
    }

    @Test
    void updateEventById_Success() {
        int eventId = 1;
        EventDto eventDto = new EventDto("Updated Event", "Updated Description", Instant.now(), Instant.now());
        Event existingEvent = new Event(eventId, "Existing Event", "Description", Instant.now(), Instant.now(), null);
        Event updatedEvent = new Event(eventId, "Updated Event", "Updated Description", Instant.now(), Instant.now(), null);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEvent);

        Event result = eventService.updateEventById(eventId, eventDto);

        verify(eventRepository, times(1)).findById(eventId);
        verify(eventRepository, times(1)).save(any(Event.class));
        assertEquals(updatedEvent, result);
    }

    @Test
    void updateEventById_NotFound() {
        int eventId = 1;
        EventDto eventDto = new EventDto("Updated Event", "Updated Description", Instant.now(), Instant.now());

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(SecurityException.class, () -> eventService.updateEventById(eventId, eventDto));

        verify(eventRepository, times(1)).findById(eventId);

    }

    @Test
    void deleteEventByTitle_Success() {
        String eventTitle = "Test Event";
        Event event = new Event(1, eventTitle, "Description", Instant.now(), Instant.now(), null);

        when(eventRepository.findByTitle(eventTitle)).thenReturn(Optional.of(event));

        eventService.deleteEventByTitle(eventTitle);

        verify(eventRepository, times(1)).findByTitle(eventTitle);
        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    void deleteEventByTitle_NotFound() {
        String eventTitle = "Non-existent Event";

        when(eventRepository.findByTitle(eventTitle)).thenReturn(Optional.empty());

        assertThrows(SecurityException.class, () -> eventService.deleteEventByTitle(eventTitle));

        verify(eventRepository, times(1)).findByTitle(eventTitle);
    }

    @Test
    void findAll() {
        Pageable pageable = Pageable.unpaged();
        Instant startTime = Instant.now();
        Instant endTime = Instant.now().plusSeconds(3600);
        Event event = new Event(1, "Test Event", "Description", startTime, endTime, null);
        Page<Event> expectedPage = new PageImpl<>(Collections.singletonList(event));

        when(eventRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Event> result = eventService.findAll(pageable, null, null);

        assertEquals(expectedPage, result);
    }
}