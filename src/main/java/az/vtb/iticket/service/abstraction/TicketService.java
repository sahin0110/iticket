package az.vtb.iticket.service.abstraction;

import az.vtb.iticket.dao.entity.TicketEntity;
import az.vtb.iticket.model.criteria.TicketCriteria;
import az.vtb.iticket.model.request.CreateTicketRequest;
import az.vtb.iticket.model.response.TicketResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
    void saveTicket(@Valid CreateTicketRequest ticketRequest);

    Page<TicketResponse> getAllTicket(TicketCriteria ticketCriteria, Pageable pageable);

    TicketResponse getTicket(Long ticketId);

    TicketEntity fetchTicketIfExist(Long ticketId);

    void deleteTicket(Long ticketId);

    TicketResponse updateTicket(Long ticketId, @Valid CreateTicketRequest ticketRequest);
}
