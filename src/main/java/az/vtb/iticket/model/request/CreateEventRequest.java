package az.vtb.iticket.model.request;

import az.vtb.iticket.model.enums.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static az.vtb.iticket.model.constant.DateTimeConstants.DATE_TIME_PATTERN;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreateEventRequest {

    @NotBlank(message = "validation.not.blank-name")
    String name;

    @Size(max = 1000, message = "validation.max.size-description")
    @NotBlank(message = "validation.not.blank-description")
    String description;

    @NotBlank(message = "validation.not.blank-location")
    String location;

    @NotNull(message = "validation.not.blank-category")
    Category category;

    @NotNull(message = "validation.not.null-startTime")
//    @FutureOrPresent(message = "validation.startTime.futureOrPresent")
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime startTime;

    @NotNull(message = "validation.not.null-endTime")
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    LocalDateTime endTime;
}
