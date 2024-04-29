package com.example.project.controller;


import com.example.project.dto.EventDto;
import com.example.project.model.Event;
import com.example.project.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Event saveEvent(@RequestBody EventDto eventDto) {
        return eventService.createEvent(eventDto);
    }

    @RequestMapping(value = "/updateById/{id}", method = RequestMethod.POST)
    public Event updateEventById(@PathVariable("id") Integer id, @RequestBody EventDto eventDto) {
        return eventService.updateEventById(id, eventDto);
    }

    @RequestMapping(value = "/{title}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable(value = "title") String title) {
        eventService.deleteEventByTitle(title);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page<Event> findAll(@RequestParam(required = false) Instant startTime,
                               @RequestParam(required = false) Instant startTime1,
                               @RequestParam(defaultValue = "0") Integer pageNumber,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        return eventService.findAll(paging, startTime, startTime1);
    }
}
