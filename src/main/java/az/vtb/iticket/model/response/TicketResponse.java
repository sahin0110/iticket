package az.vtb.iticket.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class TicketResponse {

    private Long id;
    private BigDecimal price;
    private Integer row;
    private Integer place;
    private EventResponse event;
}
