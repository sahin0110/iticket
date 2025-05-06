package az.vtb.iticket.model.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class TicketCriteria {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer row;
    private Integer place;
    private Long eventId;
    private Boolean isDeleted;
}
