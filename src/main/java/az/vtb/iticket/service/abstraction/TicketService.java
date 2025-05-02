package az.vtb.iticket.service.abstraction;

import az.vtb.iticket.dao.entity.TicketEntity;
import az.vtb.iticket.model.criteria.PageCriteria;
import az.vtb.iticket.model.criteria.TicketCriteria;
import az.vtb.iticket.model.request.CreateTicketRequest;
import az.vtb.iticket.model.request.UpdateTicketRequest;
import az.vtb.iticket.model.response.PageableResponse;
import az.vtb.iticket.model.response.TicketResponse;
import jakarta.validation.Valid;

public interface TicketService {
    void saveTicket(@Valid CreateTicketRequest ticketRequest);

    PageableResponse<TicketResponse> getAllTickets(PageCriteria pageCriteria, TicketCriteria ticketCriteria);

    TicketResponse getTicket(Long ticketId);

    TicketEntity fetchTicketIfExist(Long ticketId);

    void deleteTicket(Long ticketId);

    TicketResponse updateTicket(Long ticketId, @Valid UpdateTicketRequest ticketRequest);
}
