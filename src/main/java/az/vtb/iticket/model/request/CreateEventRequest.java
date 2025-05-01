package az.vtb.iticket.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CreateEventRequest {

    @NotBlank(message = "validation.not.blank-name")
    private String name;

    @Size(max = 1000, message = "validation.max.size-description")
    private String description;

    @NotBlank(message = "validation.not.blank-location")
    private String location;

    @NotBlank(message = "validation.not.blank-category")
    private String category;

    @NotNull(message = "validation.not.null-startTime")
    @FutureOrPresent(message = "validation.startTime.futureOrPresent")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @NotNull(message = "validation.not.null-endTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @AssertTrue(message = "validation.endTime.afterStart")
    public boolean isEndTimeAfterStartTime() {
        return endTime.isAfter(startTime);
    }
}
