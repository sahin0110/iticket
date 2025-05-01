package az.vtb.iticket.controller;

import az.vtb.iticket.model.criteria.EventCriteria;
import az.vtb.iticket.model.request.CreateEventRequest;
import az.vtb.iticket.model.response.EventResponse;
import az.vtb.iticket.service.abstraction.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createEvent(@Valid @RequestBody CreateEventRequest eventRequest) {
        eventService.saveEvent(eventRequest);
    }

    @GetMapping
    public Page<EventResponse> getAllEvents(EventCriteria eventCriteria, Pageable pageable) {
        return eventService.getAllEvents(eventCriteria, pageable);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
    }

    @GetMapping("/{eventId}")
    public EventResponse getEventById(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }
}
