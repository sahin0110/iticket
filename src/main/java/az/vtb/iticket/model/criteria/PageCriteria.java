package az.vtb.iticket.model.criteria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.USE_DEFAULTS;
import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(USE_DEFAULTS)
@FieldDefaults(level = PRIVATE)
public class PageCriteria {
    Integer page = 0;
    Integer size = 10;
}