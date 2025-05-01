package az.vtb.iticket.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
public class CreateTicketRequest {

    @NotNull(message = "validation.not.null-eventId")
    @Positive(message = "validation.positive.eventId")
    private Long eventId;

    @NotNull(message = "validation.not.null-price")
    @DecimalMin(value = "1.00", message = "validation.min-price")
    private BigDecimal price;

    @NotNull(message = "validation.not.null-row")
    @Positive(message = "validation.positive-row")
    private Integer row;

    @NotNull(message = "validation.not.null-place")
    @Positive(message = "validation.positive-place")
    private Integer place;
}
