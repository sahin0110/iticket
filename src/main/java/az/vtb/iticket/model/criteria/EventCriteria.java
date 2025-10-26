package az.vtb.iticket.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static az.vtb.iticket.model.constant.DateTimeConstants.DATE_TIME;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EventCriteria {
    String category;
    String status;

    @DateTimeFormat(pattern = DATE_TIME)
    LocalDate fromDate;
}
