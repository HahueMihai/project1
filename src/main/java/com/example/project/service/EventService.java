package com.example.project.service;


import com.example.project.dto.EventDto;
import com.example.project.dto.ParticipantDto;
import com.example.project.model.Event;
import com.example.project.model.Participant;
import com.example.project.repository.EventRepository;
import com.example.project.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    private final ParticipantRepository participantRepository;

    public Participant createParticipant(ParticipantDto participantDto){
        LOGGER.info("Participant created successfully: {}", participantDto.getEmail());
        return participantRepository.save(Participant.builder()
                        .name(participantDto.getName())
                        .email(participantDto.getEmail())
                        .address(participantDto.getAddress())
                        .accepted(false)
                .build());
    }

    public Event createEvent(EventDto eventDto){
        LOGGER.info("Event created successfully: {}", eventDto.getTitle());
        return eventRepository.save(Event.builder()
                        .title(eventDto.getTitle())
                        .description(eventDto.getDescription())
                        .startTime(eventDto.getStartTime())
                        .endTime(eventDto.getEndTime())
                .build());
    }

    public Event updateEventById(Integer id, EventDto eventDto){
        Optional<Event> event=eventRepository.findById(id);
        if(event.isEmpty()){
            LOGGER.error("Event does not exist!");
            throw new SecurityException("Event does not exist!");
        }
        return eventRepository.save(Event.builder()
                .title(eventDto.getTitle())
                .description(eventDto.getDescription())
                .startTime(eventDto.getStartTime())
                .endTime(eventDto.getEndTime())
                .build());
    }

    public void deleteEventByTitle(String title){
        Optional<Event> event=eventRepository.findByTitle(title);
        if(event.isEmpty()){
            LOGGER.error("Event does not exist!");
            throw new SecurityException("Event does not exist!");
        }
        LOGGER.info("Event with title {} was successfully deleted.", title);
        eventRepository.delete(event.get());
    }

    public Page<Event> findAll(Pageable pageable, Instant startTime, Instant startTime1){
        if(startTime!=null && startTime1!=null){
            eventRepository.findByStartTimeBetween(startTime, startTime1, pageable);
        }
        return eventRepository.findAll(pageable);
    }

    public void assigntParticipantToEvents(String participantEmail, List<String> eventsList){
        Participant participant=participantRepository.findByEmail(participantEmail);
        List<Event> eventList=new ArrayList<>();
        for(String event : eventsList){
            Optional<Event> events=eventRepository.findByTitle(event);
            if(participant!=null && events.isPresent()){
                eventList.add(events.get());
            }
        }
        if(!eventsList.isEmpty()) {
            participant.setEventList(eventList);
            participant.setAccepted(true);
            participantRepository.save(participant);
            LOGGER.info("Participant " + participant.getName() + " successfully assigned to events.");
        }
    }



}
