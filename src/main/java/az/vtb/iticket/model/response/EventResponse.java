package az.vtb.iticket.model.response;

import az.vtb.iticket.model.enums.Category;
import az.vtb.iticket.model.enums.EventStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static az.vtb.iticket.model.constant.DateTimeConstants.DATE_TIME_PATTERN;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EventResponse {

    Long id;
    String name;
    String description;
    String location;
    Category category;
    EventStatus status;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime endTime;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime startTime;
}
