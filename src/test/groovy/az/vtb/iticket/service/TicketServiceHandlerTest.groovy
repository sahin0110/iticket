package az.vtb.iticket.service

import az.vtb.iticket.dao.entity.TicketEntity
import az.vtb.iticket.dao.repository.TicketRepository
import az.vtb.iticket.exception.NotFoundException
import az.vtb.iticket.service.abstraction.EventService
import az.vtb.iticket.service.concrete.TicketServiceHandler
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

class TicketServiceHandlerTest extends Specification {

    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    private TicketServiceHandler ticketServiceHandler
    private TicketRepository ticketRepository
    private EventService eventService

    def setup() {
        ticketRepository = Mock()
        eventService = Mock()
        ticketServiceHandler = new TicketServiceHandler(ticketRepository, eventService)
    }

    def "TestGetTicketById success case"() {
        given:
        def id = random.nextObject(Long)
        def entity = random.nextObject(TicketEntity)
        entity.setDeleted(false)

        when:
        ticketServiceHandler.getTicketById(id)

        then:
        1 * ticketRepository.findById(id) >> Optional.of(entity)
    }

    def "TestGetTicketById TicketNotFound case"() {
        given:
        def id = random.nextLong()

        when:
        ticketServiceHandler.getTicketById(id)

        then:
        1 * ticketRepository.findById(id) >> Optional.empty()
        NotFoundException ex = thrown()
        ex.message == "Ticket not found"
    }
}
