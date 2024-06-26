package com.example.project.controller;


import com.example.project.dto.EventDto;
import com.example.project.dto.ParticipantDto;
import com.example.project.model.Event;
import com.example.project.model.Participant;
import com.example.project.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Operation(summary = "Save a participant")
    @RequestMapping(value = "/saveParticipant", method = RequestMethod.POST)
    public Participant saveParticipant(@RequestBody ParticipantDto participantDto) {
        return eventService.createParticipant(participantDto);
    }


    @Operation(summary = "Create a new event")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Event saveEvent(@RequestBody EventDto eventDto) {
        return eventService.createEvent(eventDto);
    }

    @Operation(summary = "Update an existing event by ID")
    @RequestMapping(value = "/updateById/{id}", method = RequestMethod.POST)
    public Event updateEventById(@Parameter(description = "ID of the event to update") @PathVariable("id") Integer id, @RequestBody EventDto eventDto) {
        return eventService.updateEventById(id, eventDto);
    }

    @Operation(summary = "Delete an event by title")
    @RequestMapping(value = "/{title}", method = RequestMethod.DELETE)
    public void deleteEvent( @Parameter(description = "Title of the event to delete") @PathVariable(value = "title") String title) {
        eventService.deleteEventByTitle(title);
    }

    @Operation(summary = "Get a list of events")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page<Event> findAll(@RequestParam(required = false) Instant startTime,
                               @RequestParam(required = false) Instant startTime1,
                               @RequestParam(defaultValue = "0") Integer pageNumber,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        return eventService.findAll(paging, startTime, startTime1);
    }

    @Operation(summary = "Assign participant to events")
    @RequestMapping(value = "/assignParticipantToEvents", method = RequestMethod.POST)
    public void assignParticipantToEvents(@Parameter(description = "Participant email") @RequestParam String participantEmail,
                                          @Parameter(description = "List of event titles") @RequestBody List<String> eventsList){
       eventService.assigntParticipantToEvents(participantEmail, eventsList);
    }



   /** --Post api/event/save
    * Creates a new event based on the data received in an EventSDto object
    *  --Post api/event/updateById/{id}
    * Updates an existing event identified by its id with the data provided in an EventDto obj
    *  --Delete api/event/{title}
    * Deltes an event By Title
    *  --Get api/list
    * Returns a page of events with an option to filter by the start time range of the event
    *  --Post api/assignParticipantToEvents
    *  Receives an email and a list of event titles as input. Searches for the participant in the database using the provided email.
    *  If both the participant and the list of events are found, assigns the participant to the events and marks them as accepted
    *   --Post api/saveParticipant
    *   Creates a participant and sets the accepted value to false because they are not yet assigned to any event
    */
}
