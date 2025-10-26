package az.vtb.iticket.model.queue;

import az.vtb.iticket.model.request.CreateEventRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class Subscriber {
    String name;
    String description;
    String location;
    LocalDateTime starTime;
    LocalDateTime endTime;

    public static Subscriber from(CreateEventRequest eventRequest) {
        return Subscriber.builder()
                .name(eventRequest.getName())
                .description(eventRequest.getDescription())
                .location(eventRequest.getLocation())
                .starTime(eventRequest.getStartTime())
                .endTime(eventRequest.getEndTime())
                .build();
    }
}