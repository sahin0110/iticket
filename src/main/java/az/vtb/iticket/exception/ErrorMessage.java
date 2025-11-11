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
    INVALID_EVENT_TIME("error.invalidEventTime"),
    TELEGRAM_SEND_MESSAGE_FAILED("error.telegram.sendMessage.failed"),
    TELEGRAM_REGISTER_BOT_FAILED("error.telegram.registerFailed"),
    TELEGRAM_SEND_EVENT_FAILED("error.telegram.sendEvent.failed")
    ;

    private final String code;
}
