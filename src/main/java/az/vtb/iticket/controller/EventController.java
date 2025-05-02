package az.vtb.iticket.controller;

import az.vtb.iticket.model.criteria.EventCriteria;
import az.vtb.iticket.model.criteria.PageCriteria;
import az.vtb.iticket.model.request.CreateEventRequest;
import az.vtb.iticket.model.response.EventResponse;
import az.vtb.iticket.model.response.PageableResponse;
import az.vtb.iticket.service.abstraction.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createEvent(@RequestBody @Valid CreateEventRequest eventRequest) {
        eventService.saveEvent(eventRequest);
    }

    @GetMapping
    public PageableResponse<EventResponse> getAllEvents(PageCriteria pageCriteria, EventCriteria eventCriteria) {
        return eventService.getAllEvents(pageCriteria, eventCriteria);
    }

    @GetMapping("/{eventId}")
    public EventResponse getEventById(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }

    @DeleteMapping("/{eventId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
    }
}
