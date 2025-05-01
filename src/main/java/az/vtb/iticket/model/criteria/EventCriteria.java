package az.vtb.iticket.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class EventCriteria {
    private String category;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;
}
