package az.vtb.iticket.service.concrete;

import az.vtb.iticket.dao.entity.EventEntity;
import az.vtb.iticket.dao.repository.EventRepository;
import az.vtb.iticket.model.request.CreateEventRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EventServiceHandlerTest {

    @Mock
    private EventRepository eventRepository;

//    @Test
//    void createEvent() {
//        var event = new EventServiceHandler();
//        event.saveEvent();
//
//    }
}