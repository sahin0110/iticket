package az.vtb.iticket.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@AllArgsConstructor
@JsonInclude(NON_EMPTY)
public class ErrorResponse {
    private String message;
    private List<ValidationErrorDto> validationErrors;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
