package az.vtb.iticket.model.response;

import az.vtb.iticket.model.enums.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static az.vtb.iticket.model.constant.DateTimeConstants.DATE_TIME_PATTERN;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {

    private Long id;
    private String name;
    private String description;
    private String location;
    private Category category;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime endTime;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime startTime;
}
