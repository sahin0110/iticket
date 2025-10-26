package az.vtb.iticket.model.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreateTicketRequest {

    @NotNull(message = "validation.not.null-eventId")
    @Positive(message = "validation.positive.eventId")
    Long eventId;

    @NotNull(message = "validation.not.null-price")
    @DecimalMin(value = "1.00", message = "validation.min-price")
    BigDecimal price;

    @NotNull(message = "validation.not.null-row")
    @Positive(message = "validation.positive-row")
    Integer row;

    @NotNull(message = "validation.not.null-place")
    @Positive(message = "validation.positive-place")
    Integer place;
}
