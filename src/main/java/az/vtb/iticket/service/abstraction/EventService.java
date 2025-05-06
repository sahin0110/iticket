package az.vtb.iticket.service.abstraction;

import az.vtb.iticket.dao.entity.EventEntity;
import az.vtb.iticket.model.criteria.EventCriteria;
import az.vtb.iticket.model.criteria.PageCriteria;
import az.vtb.iticket.model.request.CreateEventRequest;
import az.vtb.iticket.model.response.EventResponse;
import az.vtb.iticket.model.response.PageableResponse;
import jakarta.validation.Valid;

public interface EventService {

    void saveEvent(@Valid CreateEventRequest eventRequest);

    PageableResponse<EventResponse> getAllEvents(PageCriteria pageCriteria, EventCriteria eventCriteria);

    EventEntity getActiveEventOrThrow(@Valid Long eventId);

    void deleteEvent(Long eventId);

    EventResponse getEventById(Long eventId);
}
