package az.vtb.iticket.mapper

import az.vtb.iticket.dao.entity.TicketEntity
import az.vtb.iticket.model.criteria.TicketCriteria
import az.vtb.iticket.model.request.CreateTicketRequest
import az.vtb.iticket.model.request.UpdateTicketRequest
import az.vtb.iticket.service.specification.TicketSpecification
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

import static az.vtb.iticket.mapper.TicketMapper.TICKET_MAPPER

class TicketMapperTest extends Specification {

    EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestMapRequestToEntity"() {
        given:
        def ticketRequest = random.nextObject(CreateTicketRequest)

        when:
        def ticketEntity = TICKET_MAPPER.toTicketEntity(ticketRequest)

        then:
        ticketRequest.price == ticketEntity.price
        ticketRequest.row == ticketEntity.row
        ticketRequest.place == ticketEntity.place
    }

    def "TestEntityToResponse"() {
        given:
        def ticketEntity = random.nextObject(TicketEntity)

        when:
        def ticketResponse = TICKET_MAPPER.toTicketResponse(ticketEntity)

        then:
        ticketEntity.id == ticketResponse.id
        ticketEntity.price == ticketResponse.price
        ticketEntity.row == ticketResponse.row
        ticketEntity.place == ticketResponse.place
        ticketEntity.event.getId().equals ticketResponse.event.getId()
    }

    def "TestUpdateTicket"() {
        given:
        def ticketEntity = random.nextObject(TicketEntity)
        def updateTicketRequest = random.nextObject(UpdateTicketRequest)

        when:
        TICKET_MAPPER.updateTicket(ticketEntity, updateTicketRequest)

        then:
        ticketEntity.price == updateTicketRequest.price
        ticketEntity.place == updateTicketRequest.place
        ticketEntity.row == updateTicketRequest.row
    }

    def "toTicketSpecification should create TicketSpecification from TicketCriteria"() {
        given:
        def ticketCriteria = random.nextObject(TicketCriteria)

        when:
        def specification = TICKET_MAPPER.toTicketSpecification(ticketCriteria)

        then:
        specification != null
        specification instanceof TicketSpecification
    }

}
