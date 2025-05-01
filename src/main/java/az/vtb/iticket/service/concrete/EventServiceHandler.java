package az.vtb.iticket.service.concrete;

import az.vtb.iticket.dao.entity.EventEntity;
import az.vtb.iticket.dao.repository.EventRepository;
import az.vtb.iticket.exception.NotFoundException;
import az.vtb.iticket.model.criteria.EventCriteria;
import az.vtb.iticket.model.request.CreateEventRequest;
import az.vtb.iticket.model.response.EventResponse;
import az.vtb.iticket.service.abstraction.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static az.vtb.iticket.exception.ErrorMessage.EVENT_NOT_FOUND;
import static az.vtb.iticket.mapper.EventMapper.EVENT_MAPPER;

@Service
@RequiredArgsConstructor
public class EventServiceHandler implements EventService {

    private final EventRepository eventRepository;


    @Override
    public void saveEvent(CreateEventRequest eventRequest) {
        var eventEntity = EVENT_MAPPER.toEventEntity(eventRequest);
        eventRepository.save(eventEntity);
    }

    @Override
    public EventResponse getEventById(Long eventId) {
        var eventEntity = fetchEventIfExist(eventId);
        return EVENT_MAPPER.toEventResponse(eventEntity);
    }

    @Override
    public Page<EventResponse> getAllEvents(EventCriteria eventCriteria, Pageable pageable) {
        return eventRepository.findAll(EVENT_MAPPER.toEventSpecification(eventCriteria),
                        pageable)
                .map(EVENT_MAPPER::toEventResponse);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public EventEntity fetchEventIfExist(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(EVENT_NOT_FOUND.getCode()));
    }

    @Scheduled(cron = "0 * * * * *")
    public void deleteExpiredEvents() {
        var now = LocalDateTime.now();
        var expiredEvents = eventRepository.findEventEntityByEndTimeIsBefore(now);

        eventRepository.deleteAll(expiredEvents);
    }
}
