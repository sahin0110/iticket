package az.vtb.iticket.service.concrete;

import az.vtb.iticket.dao.entity.EventEntity;
import az.vtb.iticket.dao.repository.EventRepository;
import az.vtb.iticket.exception.NotFoundException;
import az.vtb.iticket.exception.UnprocessableException;
import az.vtb.iticket.model.criteria.EventCriteria;
import az.vtb.iticket.model.criteria.PageCriteria;
import az.vtb.iticket.model.request.CreateEventRequest;
import az.vtb.iticket.model.response.EventResponse;
import az.vtb.iticket.model.response.PageableResponse;
import az.vtb.iticket.service.abstraction.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static az.vtb.iticket.exception.ErrorMessage.EVENT_NOT_FOUND;
import static az.vtb.iticket.exception.ErrorMessage.INVALID_EVENT_TIME;
import static az.vtb.iticket.mapper.EventMapper.EVENT_MAPPER;
import static az.vtb.iticket.mapper.PageableMapper.PAGEABLE_MAPPER;
import static az.vtb.iticket.model.enums.EventStatus.DELETED;
import static az.vtb.iticket.model.enums.EventStatus.PUBLISHED;
import static java.time.LocalDateTime.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceHandler implements EventService {
    private final EventRepository eventRepository;

    @Override
    public void createEvent(CreateEventRequest eventRequest) {
        validateEventTime(eventRequest);
        var event = EVENT_MAPPER.toEventEntity(eventRequest);
        event.setStatus(PUBLISHED);
        eventRepository.save(event);
    }

    @Override
    public EventResponse getEventById(Long eventId) {
        var event = getActiveEventOrThrow(eventId);
        return EVENT_MAPPER.toEventResponse(event);
    }

    @Override
    public PageableResponse<EventResponse> getAllEvents(PageCriteria pageCriteria, EventCriteria eventCriteria) {
        var events = eventRepository.findAll(
                EVENT_MAPPER.toEventSpecification(eventCriteria),
                PAGEABLE_MAPPER.toPageRequest(pageCriteria)
        );
        return PAGEABLE_MAPPER.buildPageableResponse(events, EVENT_MAPPER::toEventResponse);
    }

    @Transactional
    @Override
    public void deleteEvent(Long eventId) {
        var event = getActiveEventOrThrow(eventId);
        event.setStatus(DELETED);
        event.setDeletedAt(now());
        eventRepository.save(event);
    }

    @Override
    public EventEntity getActiveEventOrThrow(Long eventId) {
        return eventRepository.findById(eventId)
                .filter(event -> PUBLISHED.equals(event.getStatus()))
                .orElseThrow(() -> new NotFoundException(EVENT_NOT_FOUND.getCode()));
    }

    public void markCompletedEventsAsDeleted() {
        var completedEvents = eventRepository.findAllByStatus(PUBLISHED);
        completedEvents.forEach(event -> {
            if (event.getEndTime().isBefore(now())) {
                event.setStatus(DELETED);
                event.setDeletedAt(now());
            }
        });
        eventRepository.saveAll(completedEvents);
    }

    private void validateEventTime(CreateEventRequest eventRequest) {
        if (eventRequest.getStartTime().isAfter(eventRequest.getEndTime())
                || eventRequest.getStartTime().isBefore(now())
                || eventRequest.getStartTime().equals(eventRequest.getEndTime())) {
            throw new UnprocessableException(INVALID_EVENT_TIME.getCode());
        }
    }
}
