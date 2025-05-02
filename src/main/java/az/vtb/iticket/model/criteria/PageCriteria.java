package az.vtb.iticket.model.criteria;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.USE_DEFAULTS;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(USE_DEFAULTS)
public class PageCriteria {
    private Integer page = 0;
    private Integer size = 10;
}