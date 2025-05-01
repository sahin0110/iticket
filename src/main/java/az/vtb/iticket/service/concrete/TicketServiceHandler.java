package az.vtb.iticket.service.concrete;

import az.vtb.iticket.dao.entity.TicketEntity;
import az.vtb.iticket.dao.repository.TicketRepository;
import az.vtb.iticket.exception.AlreadyExistsException;
import az.vtb.iticket.exception.UnprocessableException;
import az.vtb.iticket.exception.NotFoundException;
import az.vtb.iticket.model.criteria.TicketCriteria;
import az.vtb.iticket.model.request.CreateTicketRequest;
import az.vtb.iticket.model.response.TicketResponse;
import az.vtb.iticket.service.abstraction.EventService;
import az.vtb.iticket.service.abstraction.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static az.vtb.iticket.exception.ErrorMessage.*;
import static az.vtb.iticket.mapper.TicketMapper.TICKET_MAPPER;


@Slf4j
@Service
@RequiredArgsConstructor
public class TicketServiceHandler implements TicketService {

    private final TicketRepository ticketRepository;
    private final EventService eventService;


    @Override
    public void saveTicket(CreateTicketRequest ticketRequest) {
        var event = eventService.fetchEventIfExist(ticketRequest.getEventId());

        if (event.getStartTime().isBefore(LocalDateTime.now())) {
            throw new UnprocessableException(CANNOT_CREATE_TICKET.getCode());
        }

        validateTicketPlace(ticketRequest.getEventId(), ticketRequest.getRow(), ticketRequest.getPlace());

        var ticketEntity = TICKET_MAPPER.toTicketEntity(ticketRequest);
        TICKET_MAPPER.setEventInTicketEntity(ticketEntity, event);
        ticketRepository.save(ticketEntity);
    }

    @Override
    public Page<TicketResponse> getAllTicket(TicketCriteria ticketCriteria, Pageable pageable) {
        return ticketRepository.findAll(TICKET_MAPPER.toTicketSpecification(ticketCriteria), pageable)
                .map(TICKET_MAPPER::toTicketResponse);
    }

    @Override
    public TicketResponse getTicket(Long ticketId) {
        var ticket = fetchTicketIfExist(ticketId);
        return TICKET_MAPPER.toTicketResponse(ticket);
    }

    @Override
    public TicketResponse updateTicket(Long ticketId, CreateTicketRequest ticketRequest) {
        var ticket = fetchTicketIfExist(ticketId);
        TICKET_MAPPER.updateTicket(ticket, ticketRequest);
        var ticketEntity = ticketRepository.save(ticket);
        return TICKET_MAPPER.toTicketResponse(ticketEntity);
    }

    @Override
    public void deleteTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    @Override
    public TicketEntity fetchTicketIfExist(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException(TICKET_NOT_FOUND.getCode()));
    }

    private void validateTicketPlace(Long eventId, Integer row, Integer place) {
        var exists = ticketRepository.existsByEventIdAndRowAndPlace(eventId, row, place);
        if (exists) {
            throw new AlreadyExistsException(DUPLICATE_TICKET_PLACE.getCode());
        }
    }
}
