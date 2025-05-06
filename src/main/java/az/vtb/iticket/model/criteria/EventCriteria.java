package az.vtb.iticket.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static az.vtb.iticket.model.constant.DateTimeConstants.DATE_TIME;

@Data
@Builder
@AllArgsConstructor
public class EventCriteria {
    private String category;

    @DateTimeFormat(pattern = DATE_TIME)
    private LocalDate fromDate;
    private Boolean isDeleted;
}
