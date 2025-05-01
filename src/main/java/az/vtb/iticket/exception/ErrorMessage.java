package az.vtb.iticket.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    UNEXPECTED_ERROR("error.unexpected"),
    EVENT_NOT_FOUND("error.event.notFound"),
    TICKET_NOT_FOUND("error.ticket.notFound"),
    DUPLICATE_TICKET_PLACE("error.ticket.duplicatePlace"),
    CANNOT_CREATE_TICKET("error.cannotCreateTicket"),
    ;

    private final String code;
}
