package az.vtb.iticket.service

import az.vtb.iticket.dao.entity.EventEntity
import az.vtb.iticket.dao.repository.EventRepository
import az.vtb.iticket.dao.repository.TicketRepository
import az.vtb.iticket.exception.NotFoundException
import az.vtb.iticket.exception.UnprocessableException
import az.vtb.iticket.model.request.CreateEventRequest
import az.vtb.iticket.service.concrete.EventServiceHandler
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import java.time.LocalDateTime

class EventServiceHandlerTest extends Specification {

    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    private EventServiceHandler eventServiceHandler
    private EventRepository eventRepository
    private TicketRepository ticketRepository

    def setup() {
        eventRepository = Mock()
        ticketRepository = Mock()
        eventServiceHandler = new EventServiceHandler(eventRepository, ticketRepository)
    }

    def "TestGetEventById success case"() {
        given:
        def id = random.nextLong()
        def entity = random.nextObject(EventEntity)
        entity.setDeleted(false)

        when:
        def result = eventServiceHandler.getEventById(id)

        then:
        1 * eventRepository.findById(id) >> Optional.of(entity)
        result.id == entity.id
        result.name == entity.name
        result.description == entity.description
        result.location == entity.location
        result.category == entity.category
        result.startTime == entity.startTime
        result.endTime == entity.endTime
    }

    def "TestGetEventById EventNotFound case"() {
        given:
        def id = random.nextLong()

        when:
        eventServiceHandler.getEventById(id)

        then:
        1 * eventRepository.findById(id) >> Optional.empty()
        NotFoundException ex = thrown()
        ex.message == "Event not found"
    }

    def "saveEvent should save event when valid request provided"() {
        given:
        def createEventRequest = random.nextObject(CreateEventRequest)
        createEventRequest.setStartTime(LocalDateTime.now().plusHours(1))
        createEventRequest.setEndTime(LocalDateTime.now().plusHours(2))

        when:
        eventServiceHandler.createEvent(createEventRequest)

        then:
        1 * eventRepository.save(_ as EventEntity)
    }

    def "saveEvent should throw UnprocessableException when startTime after endTime"() {
        given:

        def createEventRequest = random.nextObject(CreateEventRequest)
        createEventRequest.setStartTime(LocalDateTime.now().plusHours(2))
        createEventRequest.setEndTime(LocalDateTime.now().plusHours(1))

        when:
        eventServiceHandler.createEvent(createEventRequest)

        then:
        UnprocessableException ex = thrown()
        ex.message == "Event start time must be before end time and after now"
        0 * eventRepository.save(_)
    }

    def "saveEvent should throw UnprocessableException when startTime is in past"() {
        given:
        def createEventRequest = random.nextObject(CreateEventRequest)
        createEventRequest.setStartTime(LocalDateTime.now().minusHours(1))
        createEventRequest.setEndTime(LocalDateTime.now().plusHours(1))

        when:
        eventServiceHandler.createEvent(createEventRequest)

        then:
        UnprocessableException ex = thrown()
        ex.message == "Event start time must be before end time and after now"
        0 * eventRepository.save(_)
    }
}
