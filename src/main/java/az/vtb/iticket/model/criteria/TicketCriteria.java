package az.vtb.iticket.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class TicketCriteria {
    BigDecimal minPrice;
    BigDecimal maxPrice;
    Integer row;
    Integer place;
    Long eventId;
}
