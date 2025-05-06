package az.vtb.iticket.service.concrete;

import az.vtb.iticket.dao.entity.EventEntity;
import az.vtb.iticket.dao.repository.EventRepository;
import az.vtb.iticket.dao.repository.TicketRepository;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static az.vtb.iticket.exception.ErrorMessage.*;
import static az.vtb.iticket.mapper.EventMapper.EVENT_MAPPER;
import static az.vtb.iticket.mapper.PageableMapper.PAGEABLE_MAPPER;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceHandler implements EventService {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;


    @Override
    public void saveEvent(CreateEventRequest eventRequest) {
        validateEventTime(eventRequest);
        var eventEntity = EVENT_MAPPER.toEventEntity(eventRequest);
        eventRepository.save(eventEntity);
    }

    @Override
    public EventResponse getEventById(Long eventId) {
        var eventEntity = getActiveEventOrThrow(eventId);
        return EVENT_MAPPER.toEventResponse(eventEntity);
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

        var eventEntity = getActiveEventOrThrow(eventId);

        softDeleteTicketsByEventId(eventId);

        eventEntity.setDeleted(true);
        eventEntity.setDeletedAt(LocalDateTime.now());
        eventRepository.save(eventEntity);
    }

    @Override
    public EventEntity getActiveEventOrThrow(Long eventId) {

        return eventRepository.findById(eventId)
                .filter(event -> !event.isDeleted())
                .orElseThrow(() -> new NotFoundException(EVENT_NOT_FOUND.getCode()));
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void deleteExpiredEvents() {
        var now = LocalDateTime.now();
        var expiredEvents = eventRepository.findEventEntityByEndTimeIsBefore(now);

        var eventIds = expiredEvents.stream()
                .map(EventEntity::getId)
                .toList();

        ticketRepository.deleteByEventIds(eventIds);
        eventRepository.deleteAll(expiredEvents);
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void deleteEventsExpiredDeleteTime() {
        var expiryTime = LocalDateTime.now().minusMinutes(1);

        var expiredEvents = eventRepository.findEventEntityByDeletedAtBefore(expiryTime);

        var eventIds = expiredEvents.stream()
                .map(EventEntity::getId)
                .toList();

        ticketRepository.deleteByEventIds(eventIds);
        eventRepository.deleteAll(expiredEvents);
    }

    private void validateEventTime(CreateEventRequest eventRequest) {
        if (eventRequest.getStartTime().isAfter(eventRequest.getEndTime())
                || eventRequest.getStartTime().isBefore(LocalDateTime.now())) {
            throw new UnprocessableException(INVALID_EVENT_TIME.getCode());
        }
    }

    private void softDeleteTicketsByEventId(Long eventId) {
        var tickets = ticketRepository.findAllByEventId(eventId);
        LocalDateTime deletedAt = LocalDateTime.now();

        tickets.forEach(ticket -> {
            ticket.setDeleted(true);
            ticket.setDeletedAt(deletedAt);
        });

        ticketRepository.saveAll(tickets);
    }
}
