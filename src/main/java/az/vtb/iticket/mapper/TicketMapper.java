package az.vtb.iticket.mapper;

import az.vtb.iticket.dao.entity.EventEntity;
import az.vtb.iticket.dao.entity.TicketEntity;
import az.vtb.iticket.model.criteria.TicketCriteria;
import az.vtb.iticket.model.request.CreateTicketRequest;
import az.vtb.iticket.model.response.TicketResponse;
import az.vtb.iticket.service.specification.TicketSpecification;

import java.util.List;

import static az.vtb.iticket.mapper.EventMapper.EVENT_MAPPER;

public enum TicketMapper {
    TICKET_MAPPER;

    public TicketEntity toTicketEntity(CreateTicketRequest ticketRequest) {
        return TicketEntity.builder()
                .price(ticketRequest.getPrice())
                .row(ticketRequest.getRow())
                .place(ticketRequest.getPlace())
                .build();
    }

    public TicketResponse toTicketResponse(TicketEntity ticketEntity) {
        return TicketResponse.builder()
                .id(ticketEntity.getId())
                .price(ticketEntity.getPrice())
                .row(ticketEntity.getRow())
                .place(ticketEntity.getPlace())
                .event(EVENT_MAPPER.toEventResponse(ticketEntity.getEvent()))
                .build();
    }

    public void updateTicket(TicketEntity ticketEntity, CreateTicketRequest ticketRequest) {

        if (ticketRequest.getPrice() != null) {
            ticketEntity.setPrice(ticketRequest.getPrice());
        }

        if (ticketRequest.getRow() != null) {
            ticketEntity.setRow(ticketRequest.getRow());
        }

        if (ticketRequest.getPlace() != null) {
            ticketEntity.setPlace(ticketRequest.getPlace());
        }
    }

    public TicketSpecification toTicketSpecification(TicketCriteria ticketCriteria) {
        return new TicketSpecification(ticketCriteria);
    }

    public void setEventInTicketEntity(TicketEntity ticketEntity, EventEntity eventEntity) {
        ticketEntity.setEvent(eventEntity);
        eventEntity.setTicketEntities(List.of(ticketEntity));
    }
}
