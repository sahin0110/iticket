package az.vtb.iticket.controller;

import az.vtb.iticket.model.criteria.TicketCriteria;
import az.vtb.iticket.model.request.CreateTicketRequest;
import az.vtb.iticket.model.response.TicketResponse;
import az.vtb.iticket.service.abstraction.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void createTicket(@Valid @RequestBody CreateTicketRequest ticketRequest) {
        ticketService.saveTicket(ticketRequest);
    }

    @GetMapping
    public Page<TicketResponse> allTickets(TicketCriteria ticketCriteria, Pageable pageable) {
        return ticketService.getAllTicket(ticketCriteria, pageable);
    }

    @GetMapping("/{ticketId}")
    public TicketResponse getTicket(@PathVariable Long ticketId) {
        return ticketService.getTicket(ticketId);
    }

    @PutMapping("/{ticketId}")
    public TicketResponse updateTicket(@PathVariable Long ticketId,
                                       @Valid @RequestBody CreateTicketRequest ticketRequest) {
        return ticketService.updateTicket(ticketId, ticketRequest);
    }

    @DeleteMapping("/{ticketId}")
    public void deleteTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
    }
}