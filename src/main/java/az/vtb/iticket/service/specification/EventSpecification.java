package az.vtb.iticket.service.specification;

import az.vtb.iticket.dao.entity.EventEntity;
import az.vtb.iticket.model.criteria.EventCriteria;
import az.vtb.iticket.util.PredicateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static az.vtb.iticket.dao.entity.EventEntity.Fields.category;
import static az.vtb.iticket.dao.entity.EventEntity.Fields.status;
import static az.vtb.iticket.util.PredicateUtil.applyLikePattern;

@RequiredArgsConstructor
public class EventSpecification implements Specification<EventEntity> {

    private final EventCriteria eventCriteria;

    @Override
    public Predicate toPredicate(@NonNull Root<EventEntity> root,
                                 CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder cb) {
        var predicates = PredicateUtil.builder()
                .add(
                        eventCriteria.getStatus(),
                        it -> cb.like(cb.lower(root.get(status)), applyLikePattern(it.toLowerCase()))
                )
                .addNullSafety(
                        eventCriteria.getCategory(),
                        it -> cb.like(cb.lower(root.get(category)), applyLikePattern(it.toLowerCase()))
                )
                .addNullSafety(
                        eventCriteria.getFromDate(),
                        fromDate -> {
                            LocalDateTime startOfDay = fromDate.atStartOfDay();
                            LocalDateTime endOfDay = fromDate.atTime(LocalTime.MAX);
                            return cb.between(root.get("startTime"), startOfDay, endOfDay);
                        }
                )
                .build();
        return cb.and(predicates);
    }
}
