package az.vtb.iticket.service.abstraction;


import az.vtb.iticket.dao.entity.EventEntity;
import az.vtb.iticket.model.criteria.EventCriteria;
import az.vtb.iticket.model.request.CreateEventRequest;
import az.vtb.iticket.model.response.EventResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EventService {

    void saveEvent(@Valid CreateEventRequest eventRequest);

    Page<EventResponse> getAllEvents(EventCriteria eventCriteria,Pageable pageable);

    EventEntity fetchEventIfExist(@Valid Long eventId);

    void deleteEvent(Long eventId);

    EventResponse getEventById(Long eventId);
}
