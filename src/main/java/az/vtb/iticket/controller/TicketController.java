package az.vtb.iticket.controller;

import az.vtb.iticket.model.criteria.PageCriteria;
import az.vtb.iticket.model.criteria.TicketCriteria;
import az.vtb.iticket.model.request.CreateTicketRequest;
import az.vtb.iticket.model.request.UpdateTicketRequest;
import az.vtb.iticket.model.response.PageableResponse;
import az.vtb.iticket.model.response.TicketResponse;
import az.vtb.iticket.service.abstraction.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createTicket(@RequestBody @Valid CreateTicketRequest ticketRequest) {
        ticketService.saveTicket(ticketRequest);
    }

    @GetMapping
    public PageableResponse<TicketResponse> getAllTickets(PageCriteria pageCriteria, TicketCriteria ticketCriteria) {
        return ticketService.getAllTickets(pageCriteria, ticketCriteria);
    }

    @GetMapping("/{ticketId}")
    public TicketResponse getTicketById(@PathVariable Long ticketId) {
        return ticketService.getTicketById(ticketId);
    }

    @PutMapping("/{ticketId}")
    public TicketResponse updateTicket(@PathVariable Long ticketId,
                                       @RequestBody @Valid UpdateTicketRequest ticketRequest) {
        return ticketService.updateTicket(ticketId, ticketRequest);
    }

    @DeleteMapping("/{ticketId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
    }
}