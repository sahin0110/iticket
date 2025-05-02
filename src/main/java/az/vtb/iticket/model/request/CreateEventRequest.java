package az.vtb.iticket.model.request;

import az.vtb.iticket.model.enums.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static az.vtb.iticket.model.constant.DateTimeConstants.DATE_TIME_PATTERN;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequest {

    @NotBlank(message = "validation.not.blank-name")
    private String name;

    @Size(max = 1000, message = "validation.max.size-description")
    private String description;

    @NotBlank(message = "validation.not.blank-location")
    private String location;

    @NotNull(message = "validation.not.blank-category")
    private Category category;

    @NotNull(message = "validation.not.null-startTime")
    @FutureOrPresent(message = "validation.startTime.futureOrPresent")
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime startTime;

    @NotNull(message = "validation.not.null-endTime")
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime endTime;
}
